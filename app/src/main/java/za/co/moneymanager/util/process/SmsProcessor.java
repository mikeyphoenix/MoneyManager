package za.co.moneymanager.util.process;

import za.co.moneymanager.util.SmsProcessStrategy;

/**
 * Created by michaeljacobs on 9/9/2015.
 */
public class SmsProcessor {

    private SmsProcessStrategy smsProcessStrategy;

    SmsProcessor(SmsProcessStrategy smsProcessStrategy) {
        this.smsProcessStrategy = smsProcessStrategy;
    }

    public void execute() {
        smsProcessStrategy.processSms();
    }
}
