package ca.mcmaster.cas735.acmepark.permit.business;

import ca.mcmaster.cas735.acmepark.permit.DTO.PermitCreatedDTO;
import ca.mcmaster.cas735.acmepark.permit.DTO.PermitRenewalDTO;
import ca.mcmaster.cas735.acmepark.permit.adapter.AMQPPaymentServiceListener;
import ca.mcmaster.cas735.acmepark.permit.business.entity.Permit;
import ca.mcmaster.cas735.acmepark.permit.business.entity.User;
import ca.mcmaster.cas735.acmepark.permit.business.errors.UserNotFoundException;
import ca.mcmaster.cas735.acmepark.permit.port.PaymentListenerPort;
import ca.mcmaster.cas735.acmepark.permit.port.PaymentSenderPort;
import ca.mcmaster.cas735.acmepark.permit.port.PermitRepository;
import ca.mcmaster.cas735.acmepark.permit.port.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class PermitApplicationService  {
    private final PaymentSenderPort paymentSenderPort;
    private final PermitRepository permitRepository;
    private final UserRepository userRepository;

    @Autowired
    public PermitApplicationService(PaymentSenderPort paymentSenderPort,
                                    PermitRepository permitRepository,
                                    UserRepository userRepository) {
        this.paymentSenderPort = paymentSenderPort;
        this.permitRepository = permitRepository;

        this.userRepository = userRepository;
    }


    public void applyForPermit(PermitCreatedDTO permitDTO) {
        // Logic to generate transponder number
        UUID transponderNumber = generateTransponderNumber();
        permitDTO.setTransponderNumber(transponderNumber);

        // Fetch the user entity based on the userId from the PermitCreatedDTO
        User user = userRepository.findByUserId(permitDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User with ID " + permitDTO.getUserId() + " not found"));;

        // Set the userType based on the User entity
        permitDTO.setUserType(user.getUserType().toString());




        // Send to payment service
        paymentSenderPort.initiatePayment(permitDTO);

        System.out.println("Permit application submitted and payment initiated for User ID: " + permitDTO.getUserId());
    }


    public void renewPermit(PermitRenewalDTO renewalDTO) {
        //Retrieve the permit
        Permit permit = permitRepository.findById(renewalDTO.getPermitId())
                .orElseThrow(() -> new RuntimeException("Permit not found"));

        // Prepare for payment
        PermitCreatedDTO paymentDTO = new PermitCreatedDTO();
        paymentDTO.setTransponderNumber(permit.getTransponderNumber());
        paymentDTO.setValidFrom(renewalDTO.getValidFrom());
        paymentDTO.setValidUntil(renewalDTO.getValidUntil());
        paymentDTO.setUserId(permit.getUser().getUserId());
        paymentDTO.setLotId(permit.getLotId());
        paymentDTO.setUserType(permit.getUser().getUserType().toString());

        //Send to payment service
        paymentSenderPort.initiatePayment(paymentDTO);
        System.out.println("Permit application submitted and payment initiated for User ID: " + paymentDTO.getUserId());

    }

    public void processPaymentSuccess(PermitCreatedDTO event){
        System.out.println("Checking and storing for permit: " + event);
        boolean paymentSuccess = event.isResult();

        if (paymentSuccess) {
            try {
                // Proceed with storing the permit if payment is successful
                storePermitData(event);
                System.out.println("Permit created successfully for License Plate: " + event.getLicensePlate());
            } catch (Exception e) {
                System.err.println("Error while storing permit data: " + e.getMessage());
                // Handle storage failure, e.g., retry or raise an alert
            }
        } else {
            // Log or handle the payment failure
            System.err.println("Payment failed for Permit for: " + event.getLicensePlate());
            handlePaymentFailure(event);
        }
    }

    private void handlePaymentFailure(PermitCreatedDTO event) {
        System.out.println("Notifying about payment failure for permit: " + event.getLicensePlate());
        //can future add handle method
    }





    //method to store permit data
    private void storePermitData(PermitCreatedDTO permitDTO) {
        User user = userRepository.findById(permitDTO.getUserId())
                .orElseThrow(() ->  new UserNotFoundException("User with ID " + permitDTO.getUserId() + " not found"));
        Permit permit = new Permit(
                permitDTO.getTransponderNumber(),
                permitDTO.getValidFrom(),
                permitDTO.getValidUntil(),
                user,
                permitDTO.getLotId(),
                permitDTO.getLicensePlate());
        permitRepository.save(permit);
        System.out.println("Permit save for Permit id: "+ permit.getPermitId());
    }

    private UUID generateTransponderNumber() {
        // Logic to generate a transponder number
        return UUID.randomUUID();// Example transponder number
    }

}
