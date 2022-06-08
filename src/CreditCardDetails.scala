import scala.beans.BeanProperty


class CreditCardDetails extends IProducer {
  
  @BeanProperty var bank: String  =  _;
  @BeanProperty var creditcard: Int = _;
  @BeanProperty var account: Int = _;
 
}