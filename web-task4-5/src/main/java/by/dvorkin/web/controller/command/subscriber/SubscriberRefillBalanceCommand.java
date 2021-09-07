package by.dvorkin.web.controller.command.subscriber;

import by.dvorkin.web.controller.command.Command;
import by.dvorkin.web.controller.command.Forward;
import by.dvorkin.web.model.entity.Account;
import by.dvorkin.web.model.entity.Action;
import by.dvorkin.web.model.entity.Subscriber;
import by.dvorkin.web.model.entity.SubscriberAction;
import by.dvorkin.web.model.service.ServiceFactory;
import by.dvorkin.web.model.service.SubscriberActionService;
import by.dvorkin.web.model.service.SubscriberService;
import by.dvorkin.web.model.service.exceptions.FactoryException;
import by.dvorkin.web.model.service.exceptions.ServiceException;
import by.dvorkin.web.model.service.impl.ServiceFactoryImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;

public class SubscriberRefillBalanceCommand implements Command {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        try (ServiceFactory serviceFactory = new ServiceFactoryImpl()) {
            HttpSession session = req.getSession();
            SubscriberService subscriberService = serviceFactory.getSubscriberService();
            Account account = (Account) session.getAttribute("sessionAccount");
            Subscriber subscriber = subscriberService.findByAccountId(account.getId());
            if (req.getParameter("confirmation") != null) {
                SubscriberActionService subscriberActionService = serviceFactory.getSubscriberActionService();
                int balanceRefillSum = Integer.parseInt(req.getParameter("balanceRefillRoubles")) * 100;
                balanceRefillSum += Integer.parseInt(req.getParameter("balanceRefillKopecks"));
                subscriber.setBalance(subscriber.getBalance() + balanceRefillSum);
                subscriberService.update(subscriber);
                SubscriberAction subscriberAction = createSubscriberAction(subscriber.getId(), balanceRefillSum);
                subscriberActionService.create(subscriberAction);
                req.setAttribute("success", "subscriber.refillBalanceSuccess");
                req.removeAttribute("confirmation");
                Logger logger = LogManager.getLogger("User");
                logger.info("User #" + subscriber.getId() + " refilled the balance to sum: " + balanceRefillSum);
            }
            return null;
        } catch (ServiceException | FactoryException e) {
            throw new ServletException(e);
        } catch (Exception ignored) {
        }
        return null;
    }

    private SubscriberAction createSubscriberAction(Long subscriberId, int sum) {
        SubscriberAction subscriberAction = new SubscriberAction();
        subscriberAction.setAction(Action.REFILL_BALANCE);
        subscriberAction.setSubscriberId(subscriberId);
        subscriberAction.setDate(new Date());
        subscriberAction.setSum(sum);
        return subscriberAction;
    }
}
