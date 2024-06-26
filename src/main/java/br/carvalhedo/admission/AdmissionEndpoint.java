package br.carvalhedo.admission;

import io.fabric8.kubernetes.api.model.admission.v1.AdmissionReview;
import io.fabric8.kubernetes.api.model.networking.v1.Ingress;
import io.javaoperatorsdk.webhook.admission.AdmissionController;
import io.javaoperatorsdk.webhook.admission.AsyncAdmissionController;
import io.smallrye.mutiny.Uni;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/")
public class AdmissionEndpoint {

    public static final String MUTATE_PATH = "mutate";
    public static final String VALIDATE_PATH = "validate";
    public static final String ASYNC_MUTATE_PATH = "async-mutate";
    public static final String ASYNC_VALIDATE_PATH = "async-validate";

    private final AdmissionController<Ingress> validationController;
    private final AsyncAdmissionController<Ingress> asyncValidationController;

    @Inject
    public AdmissionEndpoint(
            @Named(AdmissionControllerConfig.VALIDATING_CONTROLLER) AdmissionController<Ingress> validationController,
            @Named(AdmissionControllerConfig.ASYNC_VALIDATING_CONTROLLER) AsyncAdmissionController<Ingress> asyncValidationController) {
        this.validationController = validationController;
        this.asyncValidationController = asyncValidationController;
    }

    @POST
    @Path(VALIDATE_PATH)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public AdmissionReview validate(AdmissionReview admissionReview) {
        return validationController.handle(admissionReview);
    }

    @POST
    @Path(ASYNC_VALIDATE_PATH)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<AdmissionReview> asyncValidate(AdmissionReview admissionReview) {
        return Uni.createFrom()
                .completionStage(() -> this.asyncValidationController.handle(admissionReview));
    }
}