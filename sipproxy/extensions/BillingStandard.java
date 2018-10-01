package gov.nist.sip.proxy.extensions;

public class BillingStandard implements BillingStrategy{

	@Override
	public float calculateCost(long time){
		/*
		 * 0-30s : 0.25 euro standard
		 * 31-120s : 0.01 euro/s
		 * >120s : 0.005 euro/s
		 */
		float sec = time/1000;
		float cost;
		if (sec<30){
			cost=0.25f;
		}
		else if (sec<120){
			cost = (0.25f+(sec-30)*0.01f);
		}
		else{
			cost = (0.25f + 90*0.01f) + (sec-120)*0.005f;
		}
		return cost;
	}

}
