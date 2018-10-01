package gov.nist.sip.proxy.extensions;

public class BillingFriends implements BillingStrategy {

	@Override
	public float calculateCost(long time){
		/*
		 * 0-30s : 0.20 euro standard
		 * 31-120s : 0.005 euro/s
		 * >120s : 0.0025 euro/s
		 */
		float sec = time/1000;
		float cost;
		if (sec<30){
			cost=0.20f;
		}
		else if (sec<120){
			cost = (0.20f+(sec-30)*0.005f);
		}
		else{
			cost = (0.20f + 90*0.005f) + (sec-120)*0.00025f;
		}
		return cost;
	}
}
