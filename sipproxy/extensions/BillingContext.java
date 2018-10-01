package gov.nist.sip.proxy.extensions;

public class BillingContext {

   private BillingStrategy strategy;

   public BillingContext(BillingStrategy strategy){
      this.strategy = strategy;
   }

   public float executeStrategy(long time){
      return strategy.calculateCost(time);
   }
}
