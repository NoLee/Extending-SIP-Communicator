package gov.nist.sip.proxy.extensions;

import javax.sip.*;

public class SipListenerDec implements SipListener
{
    protected SipListener proxy;

    public SipListenerDec(SipListener proxy)
    {
        this.proxy=proxy;
    }

    @Override
    public void processRequest(RequestEvent requestEvent) {
        proxy.processRequest(requestEvent);

    }

    @Override
    public void processResponse(ResponseEvent responseEvent) {
        // TODO Auto-generated method stub
        proxy.processResponse(responseEvent);
    }

    @Override
    public void processTimeout(TimeoutEvent timeOutEvent) {
        // TODO Auto-generated method stub
        proxy.processTimeout(timeOutEvent);
    }
}
