package gov.nist.sip.proxy.extensions;

public interface BillingStrategy {
	
	public float calculateCost(long time);
	
	
}
