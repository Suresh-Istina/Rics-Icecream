package ricsicecream;

/**
 *
 * @author Istina
 */


import java.util.*;

// Customer class representing customer profile
class Customer {
       
    private static int nextCustomerId = 1; // Auto-incremented customer ID
    private int customerId;
    private String name;
    private String contact;//string because it goes with country code +94
    private String email;
    private List<Order> favoriteOrders;
    private double loyaltyPoints;
    private static List<Customer> allCustomers = new ArrayList<>(); 

    public Customer(String name, String contact, String email) {
        this.customerId = nextCustomerId++;
        this.name = name;
        this.contact = contact;
        this.email = email;
        this.favoriteOrders = new ArrayList<>();
        this.loyaltyPoints = 0.00;
        allCustomers.add(this);
    }

    
    public static List<Customer> getAllCustomers() {
        return allCustomers;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }

    public String getEmail() {
        return email;
    }

    public List<Order> getFavoriteOrders() {
        return favoriteOrders;
    }

    
    public double getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(double loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    //calculate total loyalty points
    public void addLoyaltyPoints(double points) {
        
        this.loyaltyPoints += points;
        System.out.println("Earned " + points + " loyalty points. Total points: " + loyaltyPoints);
        loyaltyPointsOffer(loyaltyPoints);//check loyalty points offer
        
    }
    
    //free ice cream offer if customer earns more than 50 points 
    public void loyaltyPointsOffer(double loyaltyPoints) {
        this.loyaltyPoints =loyaltyPoints;
        if(loyaltyPoints>50)
        {
            System.out.println("***** Congratulations ! You will get a free ice cream for earning more than 50 points *****");
        }
    }
    
    // add an order to favourite orders list
    public void addFavoriteOrder(Customer customer,int orderId) {
        Order favouriteOrder= Order.getOrderByIdAndCustomer(customer.getCustomerId(), orderId);
        if (favouriteOrder!=null) {        
            
            favoriteOrders.add(favouriteOrder);

        } else {
            System.out.println("Invalid Order ID. Please provide a valid Order ID.");
        }

    }
    
    
   
    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                ", contact='" + contact + '\'' +
                ", email='" + email + '\'' +
                ", loyalty points='" + loyaltyPoints + '\'' +
                ", \nfavoriteOrders=" + favoriteOrders +
                '}';
    }
    
  

}


// Request class representing an order customization request
class CustomizationRequest {
    private String flavor;
    private List<String> toppings;
    private List<String> syrups;
    private String name;
    

    public CustomizationRequest(String flavor, List<String> toppings, List<String> syrups, String name) {
        this.flavor = flavor;
        this.toppings = toppings;
        this.syrups = syrups;
        this.name = name;
    }

    public String getFlavor() {
        return flavor;
    }

    public List<String> getToppings() {
        return toppings;
    }

    public List<String> getSyrups() {
        return syrups;
    }

    public String getName() {
        return name;
    }
}

// Product class representing a single ice cream
class CustomIceCream {
    private String flavor;
    private List<String> toppings;
    private List<String> syrups;
    private String name;
    private float iceCreamCost;

    
   public CustomIceCream(String flavor, List<String> toppings, List<String> syrups, String name,float iceCreamCost) {
    this.flavor = flavor;
    this.toppings = new ArrayList<>(toppings); 
    this.syrups = new ArrayList<>(syrups);
    this.name = name;
    this.iceCreamCost=iceCreamCost;
}

    public float getIceCreamCost() {
        return iceCreamCost;
    }

    public void setIceCreamCost(float iceCreamCost) {
        this.iceCreamCost = iceCreamCost;
    }

    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }
   

    @Override
    public String toString() {
        return "CustomIceCream{" +
                "flavor='" + flavor + '\'' +
                ", toppings=" + toppings +
                ", syrups=" + syrups +
                ", cost=" + iceCreamCost +
                ", name='" + name + '\'' +
                '}';
    }

    
}

//Builder Pattern

// Builder interface to construct a CustomIceCream object
interface IceCreamBuilder {
    IceCreamBuilder chooseFlavor(String flavor);
    IceCreamBuilder addToppings(List<String> toppings);
    IceCreamBuilder addSyrups(List<String> syrups);
    IceCreamBuilder chooseName(String name);
    CustomIceCream build();
}

// Concrete builder implementing the IceCreamBuilder to build normal icecream
class NormalIceCreamBuilder implements IceCreamBuilder {
    private String flavor;
    private List<String> toppings = new ArrayList<>();
    private List<String> syrups = new ArrayList<>();
    private String name;

    @Override
    public IceCreamBuilder chooseFlavor(String flavor) {
        this.flavor = flavor;
        return this;
    }

    @Override
    public IceCreamBuilder addToppings(List<String> toppings) {
        this.toppings.addAll(toppings);
        return this;
    }

    @Override
    public IceCreamBuilder addSyrups(List<String> syrups) {
        this.syrups.addAll(syrups);
        return this;
    }

    @Override
    public IceCreamBuilder chooseName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public CustomIceCream build() {
        
        //calculate cost of normal icecream with toppings and syrup
        float toppingsCost = toppings.size() * 10.00f; //  each topping costs 10
        float syrupsCost = syrups.size() * 20.00f; //  each syrup costs 20
        float iceCreamCost = 25.00f + toppingsCost + syrupsCost;

        System.out.println("Normal Ice Cream cost: " + iceCreamCost+ "\n");

        return new CustomIceCream(flavor, toppings, syrups, name != null ? name : "Default Name",iceCreamCost);
        
    }

   
    
   
}

// Concrete builder implementing the IceCreamBuilder interface to build gelato ice cream
class GelatoBuilder implements IceCreamBuilder {
    private String flavor;
    private List<String> toppings = new ArrayList<>();
    private List<String> syrups = new ArrayList<>();
    private String name;

    @Override
    public IceCreamBuilder chooseFlavor(String flavor) {
        this.flavor = flavor;
        return this;
    }

    @Override
    public IceCreamBuilder addToppings(List<String> toppings) {
        this.toppings.addAll(toppings);
        return this;
    }

    @Override
    public IceCreamBuilder addSyrups(List<String> syrups) {
        this.syrups.addAll(syrups);
        return this;
    }

    @Override
    public IceCreamBuilder chooseName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public CustomIceCream build() {
        
        //calculate cost of gelato with toppings and syrup
        float toppingsCost = toppings.size() * 20.00f; //each topping costs 20
        float syrupsCost = syrups.size() * 30.00f; //each syrup costs 30
        float iceCreamCost = 50.00f + toppingsCost + syrupsCost;

        System.out.println("Gelato cost: " + iceCreamCost +"\n");
        
        return new CustomIceCream(flavor, toppings, syrups, name != null ? name : "My Ice Cream",iceCreamCost);
    }

   
   
}

//Chain of Responsibility Pattern
// Handler interface for processing customization requests
interface CustomizationHandler {
    void handleRequest(CustomizationRequest request, IceCreamBuilder builder);
    void setNextHandler(CustomizationHandler nextHandler);
}

// Concrete handler for flavor customization
class FlavorHandler implements CustomizationHandler {
    private CustomizationHandler nextHandler;

    @Override
    public void handleRequest(CustomizationRequest request, IceCreamBuilder builder) {
        builder.chooseFlavor(request.getFlavor());
        System.out.println("Handling flavor customization: " + request.getFlavor());
        // Additional processing logic for flavor customization
        if (nextHandler != null) {
            nextHandler.handleRequest(request, builder);
        }
    }

    @Override
    public void setNextHandler(CustomizationHandler nextHandler) {
        this.nextHandler = nextHandler;
    }
}

// Concrete handler for topping customization
class ToppingHandler implements CustomizationHandler {
    private CustomizationHandler nextHandler;

    @Override
    public void handleRequest(CustomizationRequest request, IceCreamBuilder builder) {
        builder.addToppings(request.getToppings());
        System.out.println("Handling topping customization: " + request.getToppings());
        // Additional processing logic for topping customization
        if (nextHandler != null) {
            nextHandler.handleRequest(request, builder);
        }
    }

    @Override
    public void setNextHandler(CustomizationHandler nextHandler) {
        this.nextHandler = nextHandler;
    }
}

// Concrete handler for syrup customization
class SyrupHandler implements CustomizationHandler {
    private CustomizationHandler nextHandler;

    @Override
    public void handleRequest(CustomizationRequest request, IceCreamBuilder builder) {
        builder.addSyrups(request.getSyrups());
        System.out.println("Handling syrup customization: " + request.getSyrups());
        // Additional processing logic for syrup customization
        if (nextHandler != null) {
            nextHandler.handleRequest(request, builder);
        }
    }

    @Override
    public void setNextHandler(CustomizationHandler nextHandler) {
        this.nextHandler = nextHandler;
    }
}

// Concrete handler for setting ice cream name
class NameHandler implements CustomizationHandler {
    private CustomizationHandler nextHandler;

    @Override
    public void handleRequest(CustomizationRequest request, IceCreamBuilder builder) {
        builder.chooseName(request.getName());
        System.out.println("Setting ice cream name: " + request.getName());
        // Additional processing logic for name customization
        if (nextHandler != null) {
            nextHandler.handleRequest(request, builder);
        }
    }

    @Override
    public void setNextHandler(CustomizationHandler nextHandler) {
        this.nextHandler = nextHandler;
    }
}



// Order class representing an order containing multiple ice creams and the customer who placed the order
class Order {
    private static int nextOrderId = 1; // Auto-incremented order ID
    private int orderId;
    private float totalCost;
    private String feedback;
    private int rating;
    private List<CustomIceCream> iceCreams;
    private Customer customer;
    private OrderState state; 
    private static List<Order> allOrders = new ArrayList<>(); 
    private OrderObserver observer;
  
    
    public Order(Customer customer) {
        this.orderId = nextOrderId++;
        this.iceCreams = new ArrayList<>();
        this.customer = customer;
        this.state = new PlacedState(); // Initialize the state to "Placed" when the order is created
               
    }
    
    //find order using order id and customer id
    public static Order getOrderByIdAndCustomer(int customerId, int OrderId)
    {
        for (Order foundOrder : allOrders) {
            if(foundOrder.getOrderId()==OrderId && foundOrder.getCustomer().getCustomerId()==customerId )
            {
                return foundOrder;
            }
        }
        return null;
    }


    
    public OrderObserver getObserver() {
        return observer;
    }

    public void setObserver(OrderObserver observer) {
        this.observer = observer;
    }

    public void notifyObservers() {
        observer.update(this);     
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

  
    public int getOrderId() {
        return orderId;
    }

     
    public void processOrder() {
        state.processOrder(this);
    }

    public String getOrderState() {
        return state.getStateName();
    }

  
    public void setState(OrderState state) {
        this.state = state;
        notifyObservers(); // Notify observers only if the state changes
        
    }

    public void addIceCream(CustomIceCream iceCream) {
        iceCreams.add(iceCream);
    }

    public List<CustomIceCream> getIceCreams() {
        return iceCreams;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public static List<Order> getAllOrders() {
        return allOrders;
    }
    
    public static void addOrder (Order builtOrder) {
       allOrders.add(builtOrder);
    }
    

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
        
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
    
    //provide feedback using command pattern
    public static void provideFeedback( int customerId, int OrderId, String feedback, int rating) {
        CommandInvoker commandInvoker = new CommandInvoker();
        
        Order feedbackOrder= Order.getOrderByIdAndCustomer(customerId, OrderId);
        if(feedbackOrder!=null)
        {
            Command feedbackCommand = new ProvideFeedbackCommand(feedbackOrder, feedback, rating);
            commandInvoker.addCommand(feedbackCommand);
            commandInvoker.executeCommands();
        }
        else{
            System.out.println("Sorry you don't have an order in that ID.");
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Order{\n");
        result.append("  OrderId: ").append(orderId).append("\n");
        result.append("  Customer: ").append(customer.getName()).append(" (ID: ").append(customer.getCustomerId()).append(")\n");
        for (CustomIceCream iceCream : iceCreams) {
            result.append("  ").append(iceCream).append("\n");
        }
        result.append("  Total Cost=").append(totalCost).append("\n"); // Display the total cost
        result.append("  Feedback: ").append(feedback).append("\n");
        result.append("  Rating:  ").append(rating).append("\n");
        result.append("}");
        return result.toString();
    }
}


//Builder Pattern
// OrderBuilder interface. This can be implemented to build different types of orders in future
interface OrderBuilder {
    OrderBuilder addIceCream(CustomIceCream iceCream);
    OrderBuilder setCustomer(Customer customer); 
    OrderBuilder calculateTotalCost(); 
    OrderBuilder applyGiftWrapping(); 
    OrderBuilder applySpecialPackaging(); 
    OrderBuilder chooseDeliveryMethod(DeliveryService deliveryService, String destination);
    OrderBuilder applyPromotion(PromotionStrategy promotionStrategy); 
    OrderBuilder choosePaymentMethod(PaymentStrategy paymentStrategy);
 
    Order build();
}

//  ConcreteOrderBuilder
class ConcreteOrderBuilder implements OrderBuilder {
    private Order order;
    private float totalCost; 
    private boolean shouldApplyGiftWrapping; 
    private boolean shouldApplySpecialPackaging;
    private DeliveryService deliveryService;
    private String deliveryDestination; 
    private PromotionStrategy promotionStrategy; 
    private PaymentStrategy paymentStrategy;

    public ConcreteOrderBuilder() {
        this.order = new Order(null);
        this.totalCost = 0.00f;
        this.shouldApplyGiftWrapping = false; 
        this.shouldApplySpecialPackaging = false;
    }

    @Override
    public OrderBuilder addIceCream(CustomIceCream iceCream) {
        order.addIceCream(iceCream);
        totalCost += iceCream.getIceCreamCost(); // Add the cost of the ice cream to the total cost
        return this;
    }

    @Override
    public OrderBuilder setCustomer(Customer customer) {
        order.setCustomer(customer);
        order.setObserver(new CustomerOrderObserver(customer));
        return this;
    }
    
   
    
    @Override
    public OrderBuilder chooseDeliveryMethod(DeliveryService deliveryService, String destination) {
        this.deliveryService = deliveryService;
        this.deliveryDestination = destination;
        return this;
    }

    @Override
    public OrderBuilder calculateTotalCost() {
        // Calculate the total cost without the decorator
        float baseTotalCost = totalCost;

        // If applicable, add the cost of gift wrapping
        if (shouldApplyGiftWrapping) {
            baseTotalCost += new GiftWrappingDecorator(order).decoratorPrice();
        }

        // If applicable, add the cost of special packaging
        if (shouldApplySpecialPackaging) {
            baseTotalCost += new SpecialPackagingDecorator(order).decoratorPrice();
        }
        
        // If applicable,apply the promotion strategy
        if (promotionStrategy != null) {
            baseTotalCost = promotionStrategy.applyPromotion(baseTotalCost);
        }

        order.getCustomer().addLoyaltyPoints(baseTotalCost/10); // Earn 1 point for every 10 rupees spent
       
        // If delivery is chosen, add the cost and time estimates from the adapter
        if (deliveryService != null && deliveryDestination != null) {
            float deliveryCost = deliveryService.calculateDeliveryCost(deliveryDestination);
            float deliveryTime = deliveryService.estimateDeliveryTime(deliveryDestination);

            baseTotalCost += deliveryCost;

            System.out.println("Delivery to " + deliveryDestination +
                    ": Cost : " + deliveryCost + ", Estimated Time : " + deliveryTime + " mins");
        }
        
        // Set the total cost in the Order class
        order.setTotalCost(baseTotalCost);
       
        return this;
    }

    @Override
    public OrderBuilder applyPromotion(PromotionStrategy promotionStrategy) {
        this.promotionStrategy = promotionStrategy;
        return this;
    }
    
   @Override
    public OrderBuilder applyGiftWrapping() {
        shouldApplyGiftWrapping = true;
        return this;
    }

    @Override
    public OrderBuilder applySpecialPackaging() {
         shouldApplySpecialPackaging = true;
        return this;
    }
    
    
    @Override
    public OrderBuilder choosePaymentMethod(PaymentStrategy paymentStrategy) {
         this.paymentStrategy = paymentStrategy;
        return this;
    }

    
     
    @Override
    public Order build() {
        calculateTotalCost();
        if (paymentStrategy != null) {
            float totalCost = order.getTotalCost();
            paymentStrategy.processPayment(totalCost);
        }
        order.processOrder();    //can be called again to change the state
        Order.addOrder(order);
        
        return order;
    }

 

    
}

//Decorator pattern
// OrderDecorator class representing a decorator for the Order class
abstract class OrderDecorator extends Order {
    protected Order decoratedOrder;

    public OrderDecorator(Order decoratedOrder) {
        super(decoratedOrder.getCustomer());
        this.decoratedOrder = decoratedOrder;
    }

    
    public float decoratorPrice() {
       return 0.00f;
    }

    @Override
    public List<CustomIceCream> getIceCreams() {
        return decoratedOrder.getIceCreams();
    }
}

// Concrete decorator for gift wrapping
class GiftWrappingDecorator extends OrderDecorator {
    public GiftWrappingDecorator(Order decoratedOrder) {
        super(decoratedOrder);
    }

    @Override
    public float decoratorPrice() {      
       System.out.println("Gift Wrapping Charges: 100.00 " );
       return 100.00f;
    }

   
}

// Concrete decorator for special packaging
class SpecialPackagingDecorator extends OrderDecorator {
    public SpecialPackagingDecorator(Order decoratedOrder) {
        super(decoratedOrder);
    }

    @Override
     public float decoratorPrice() {
       System.out.println("Special Packaging Charges: 200.00 " );
       return 200.00f;
    }

   
}


// DeliveryService interface representing the delivery functionality
interface DeliveryService {
    float calculateDeliveryCost(String destination);
    float estimateDeliveryTime(String destination);
}

// Mapping service that provides delivery cost and time estimates
class MappingService {
    public float getDeliveryCost(String destination) {
        // Determine delivery cost based on destination
        // return 50.00 for Wattala, 100.00 for Colombo
        // Default cost 150.00 for other location
        return destination.equalsIgnoreCase("Wattala") ? 50.00f :
               destination.equalsIgnoreCase("Colombo") ? 100.00f : 150.00f;
    }

    public float getDeliveryTime(String destination) {
        // Estimate delivery time based on destination
        // return 30mins for Wattala, 45mins for Colombo
        // Default time 60 mins for other location
        return destination.equalsIgnoreCase("Wattala") ? 30.0f :
               destination.equalsIgnoreCase("Colombo") ? 45.0f : 60.0f;
    }
}

// Concrete implementation of the DeliveryService interface
class MappingServiceAdapter implements DeliveryService {
    private MappingService mappingService;

    public MappingServiceAdapter(MappingService mappingService) {
        this.mappingService = mappingService;
    }

    @Override
    public float calculateDeliveryCost(String destination) {
        // Use the mapping service to determine the delivery cost
        float deliveryCost = mappingService.getDeliveryCost(destination);
        return deliveryCost;
    }

    @Override
    public float estimateDeliveryTime(String destination) {
        // Use the mapping service to estimate delivery time
        float deliveryTime = mappingService.getDeliveryTime(destination);
        return deliveryTime;
    }
}



// State pattern
// OrderState interface representing different states of an order
interface OrderState {
    void processOrder(Order order);

    String getStateName();
}

// Concrete implementation of OrderState for "Placed" state
class PlacedState implements OrderState {
    @Override
    public void processOrder(Order order) {
        System.out.println("Order is placed. Processing...");
        order.setState(new InPreparationState());
        
    }

    @Override
    public String getStateName() {
        return "Placed";
    }
}

// Concrete implementation of OrderState for "In Preparation" state
class InPreparationState implements OrderState {
    @Override
    public void processOrder(Order order) {
        System.out.println("Order is in preparation. Preparing...");
        order.setState(new OutForDeliveryState());
    }

    @Override
    public String getStateName() {
        return "In Preparation";
    }
}

// Concrete implementation of OrderState for "Out for Delivery" state
class OutForDeliveryState implements OrderState {
    @Override
    public void processOrder(Order order) {
        System.out.println("Order is out for delivery. Delivering...");
        order.setState(new DeliveredState());
    }

    @Override
    public String getStateName() {
        return "Out for Delivery";
    }
}

// Concrete implementation of OrderState for "Delivered" state
class DeliveredState implements OrderState {
    @Override
    public void processOrder(Order order) {
        System.out.println("Order is delivered. Thank you!");
    }

    @Override
    public String getStateName() {
        return "Delivered";
    }
}

//Observer Pattern
// Observer interface for order status updates
interface OrderObserver {
    void update(Order order);
}

// Concrete observer for customers
class CustomerOrderObserver implements OrderObserver {
    private Customer customer;

    public CustomerOrderObserver(Customer customer) {
        this.customer = customer;
       
    }

     @Override
    public void update(Order order) {
        System.out.println("Dear " + customer.getName() + ", your order status for Order ID " +order.getOrderId()+ " is now: " + order.getOrderState());
    }
}

//Strategy pattern
// PromotionStrategy interface representing different promotion strategies
interface PromotionStrategy {
    float applyPromotion(float totalCost);
}


// Concrete implementation of PromotionStrategy for seasonal discount
class SeasonalDiscount implements PromotionStrategy {
    private float discountPercentage;

    public SeasonalDiscount(float discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    @Override
    public float applyPromotion(float totalCost) {
        float discountAmount = totalCost * (discountPercentage / 100.0f);
        System.out.println("Seasonal Discount of "+discountPercentage+"%.  Discount amount : "+discountAmount);
        System.out.println("Cost Before Discount "+totalCost+".  Cost After Discount : "+(totalCost - discountAmount));
        return totalCost - discountAmount;
    }
}

// Concrete implementation of PromotionStrategy for flavour discount
//can apply flavour discount by passing request when building order
class FlavorDiscountPromotion implements PromotionStrategy {
    
    //flavours eligible for discount
    private static final Set<String> discountFlavours = Set.of("Chocolate", "Strawberry");
    private static final float flavourDiscountPercentage = 10.0f;

    private CustomizationRequest request; 
    
    

    public FlavorDiscountPromotion(CustomizationRequest request) {
        this.request = request;
    }

    @Override
    public float applyPromotion(float totalCost) {
        System.out.println("Checking flavors for discount...");

        // Check if any of the requested flavors are eligible for the discount
        boolean hasDiscountedFlavor = discountFlavours.stream()
                .anyMatch(flavor -> flavor.equalsIgnoreCase(request.getFlavor()));

        if (hasDiscountedFlavor) {
            float discountAmount = totalCost * (flavourDiscountPercentage / 100.0f);
            System.out.println("Flavour discount of " + flavourDiscountPercentage + "% on total bill. Discount amount: " + discountAmount);
            return totalCost - discountAmount;
        } else {
            return totalCost;
        }
    }
}


//Strategy Pattern
// PaymentStrategy interface representing different payment methods
interface PaymentStrategy {
    void processPayment(float amount);
}

// Concrete implementation of PaymentStrategy for Credit Card payments
class CreditCardPaymentStrategy implements PaymentStrategy {
    private String cardNumber;
    private String expiryDate;
    private String cvv;

    public CreditCardPaymentStrategy(String cardNumber, String expiryDate, String cvv) {
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }

    @Override
    public void processPayment(float amount) {
        System.out.println("Processing Credit Card Payment of Rs." + amount);
    }
}

// Concrete implementation of PaymentStrategy for Digital Wallet payments
class DigitalWalletPaymentStrategy implements PaymentStrategy {
    private String walletId;

    public DigitalWalletPaymentStrategy(String walletId) {
        this.walletId = walletId;
    }

    @Override
    public void processPayment(float amount) {
        System.out.println("Processing Digital Wallet Payment of Rs." + amount);
     }
}

// Concrete implementation of PaymentStrategy for Cash payments
class CashPaymentStrategy implements PaymentStrategy {
    @Override
    public void processPayment(float amount) {
        System.out.println("Collecting Cash Payment of Rs." + amount);
       
    }
}


// Command Pattern
interface Command {
    void execute();
}

// Concrete command for placing an order. Orders can be customized here
class PlaceOrderCommand implements Command {
   
      private Customer customer;
        
    // Create builders and handlers
      private IceCreamBuilder iceCreamBuilder ;
      private IceCreamBuilder gelatoBuilder ;        
      private OrderBuilder orderBuilder ;
      private CustomizationHandler flavorHandler;
      private CustomizationHandler toppingHandler ;
      private CustomizationHandler syrupHandler;
      private CustomizationHandler nameHandler;
              
     // Create a mapping service and an adapter
        MappingService mappingService;
        DeliveryService deliveryService ;

        


    public PlaceOrderCommand(Customer customer ) {
        this.customer=customer;
        this.iceCreamBuilder = new NormalIceCreamBuilder();
        this.gelatoBuilder = new GelatoBuilder();
        this.orderBuilder = new ConcreteOrderBuilder();
        this.mappingService = new MappingService();
        this.deliveryService = new MappingServiceAdapter(mappingService);
        this.flavorHandler = new FlavorHandler();
        this.toppingHandler = new ToppingHandler();
        this.syrupHandler = new SyrupHandler();
        this.nameHandler = new NameHandler();
        
        // Set up the chain of responsibility
        flavorHandler.setNextHandler(toppingHandler);
        toppingHandler.setNextHandler(syrupHandler);
        syrupHandler.setNextHandler(nameHandler);
        
    }

    @Override
    public void execute() {
              
        // Create customization request for normal icecream
        CustomizationRequest request1 = new CustomizationRequest(
                "Vanilla",
                List.of("Sprinkles", "Nuts"),
                List.of("Chocolate", "Caramel"),
                "Delicious Vanilla Delight"
        );

        // Create customization request for gelato
        CustomizationRequest request2 = new CustomizationRequest(
                "Chocolate",
                List.of("Whipped Cream", "Cherry","Strawberry"),
                List.of("Caramel"),
                "Choco Delight"
        );
        


        // Process the customization request      
        flavorHandler.handleRequest(request1, iceCreamBuilder);
        CustomIceCream iceCream1 = iceCreamBuilder.build();       

        flavorHandler.handleRequest(request2, gelatoBuilder);
        CustomIceCream iceCream2 = gelatoBuilder.build();
        

       // Process the first 2 customization requests and set the customer for the order
        Order order = orderBuilder.setCustomer(customer)
                .addIceCream(iceCream1)
                .addIceCream(iceCream2)
                //By default it is pickup .
                //Delivery charges will be added based on the destination when delivery is chosen using chooseDeliveryMethod()
                .chooseDeliveryMethod(deliveryService, "Wattala") // delivery to Wattala. 
                .applyGiftWrapping() // Apply gift wrapping if required
                .applySpecialPackaging() // Apply special packaging if required
                .applyPromotion(new SeasonalDiscount(10.0f))//apply any promotion if applicable
                //you can can choose any of the 3 payment methods
                .choosePaymentMethod(new CreditCardPaymentStrategy("1234-5678-9012-3456", "12/23", "123"))
                .build();
  
        
        System.out.println("Place order command executed " );
    }
}

class ReorderFavoriteOrderCommand implements Command {
    private Customer customer;
    private int favoriteOrderId;

    public ReorderFavoriteOrderCommand(Customer customer, int favoriteOrderId) {
        this.customer = customer;
        this.favoriteOrderId = favoriteOrderId;
    }

    @Override
    public void execute() {
        Order favoriteOrder= Order.getOrderByIdAndCustomer(customer.getCustomerId(), favoriteOrderId);
        if (favoriteOrder!=null) {  
            
            System.out.println("Re-Ordering Favourite Order ID :  " +favoriteOrderId);
            
           OrderBuilder orderBuilder = new ConcreteOrderBuilder();
           // Add each ice cream from the favorite order to the new order
            for (CustomIceCream iceCream : favoriteOrder.getIceCreams()) {
                orderBuilder.addIceCream(iceCream);
            }

           Order newOrder = orderBuilder
                .setCustomer(customer)
                .applyGiftWrapping()//apply wrapping if required
                .choosePaymentMethod(new CashPaymentStrategy())//choose flexible payment strategy
                .build();
            System.out.println("Re-order favourite order command executed " );
        } 
        else {
            System.out.println("Invalid Favourite Order ID. Please provide a valid Order ID.");
        }
    }
}

// Concrete command for providing feedback
class ProvideFeedbackCommand implements Command {
    private Order order;
    private String feedback;
    private int rating;

    public ProvideFeedbackCommand(Order order, String feedback, int rating) {
        this.order = order;
        this.feedback = feedback;
        this.rating = rating;
    }

    @Override
    public void execute() {
      order.setFeedback(feedback);
      order.setRating(rating);
      System.out.println("\nFeedback "+ "for Order ID "+ order.getOrderId() +" :  "+ feedback + " Rating: " + rating +"\n");
    }
}

// Invoker class to manage commands
class CommandInvoker {
    private Queue<Command> commandQueue = new LinkedList<>();

    public void addCommand(Command command) {
        commandQueue.add(command);
    }

    public void executeCommands() {
        while (!commandQueue.isEmpty()) {
            Command command = commandQueue.poll();
            command.execute();
        }
    }
}





//main class
public class RicsIceCream {
    public static void main(String[] args) {
          
        // Creating customer profiles
        Customer customer1 = new Customer("Steve Rogers", "+94766894054", "steve@gmail.com");
        Customer customer2 = new Customer("Tony Stark", "+94775467310", "stark@gmail.com");
        
        // CommandInvoker setup
        CommandInvoker commandInvoker = new CommandInvoker();    
        
        
        Command placeOrderCommand = new PlaceOrderCommand(customer1);   
        commandInvoker.addCommand(placeOrderCommand); // Add the PlaceOrderCommand to the invoker's queue
        commandInvoker.executeCommands(); // Execute PlaceOrderCommand to create order

          
       // Allowing Customer to add Feedback for orders. Command Pattern for feedback will be executed if order exists
       Order.provideFeedback(customer1.getCustomerId(),1,"Great service!", 5);
        
             
        //Allowing Customer to reorder favourite order in a single click 
        Command reorderCommand = new ReorderFavoriteOrderCommand(customer1, 1);
        commandInvoker.addCommand(reorderCommand);
        commandInvoker.executeCommands();
        
        
        // Add orders to customer's favorite orders
        customer1.addFavoriteOrder(customer1,1);
   
  
                
        // View all Customer Profiles
        List<Customer> allCustomers = Customer.getAllCustomers();
        System.out.println("\n\nAll Customers:");
        for (Customer customer : allCustomers) { 
            System.out.println(customer + "\n");
        }
                

        // View all orders
        List<Order> allOrders = Order.getAllOrders();
        System.out.println("\n\nAll Orders:");
        for (Order order : allOrders) { 
            System.out.println(order);
        }
        
        
    }
}

