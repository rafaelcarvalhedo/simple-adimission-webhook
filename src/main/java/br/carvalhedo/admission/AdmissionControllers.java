package br.carvalhedo.admission;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

import io.fabric8.kubernetes.api.model.networking.v1.Ingress;
import io.javaoperatorsdk.webhook.admission.AdmissionController;
import io.javaoperatorsdk.webhook.admission.AsyncAdmissionController;
import io.javaoperatorsdk.webhook.admission.NotAllowedException;
import io.javaoperatorsdk.webhook.admission.mutation.AsyncMutator;
import io.javaoperatorsdk.webhook.admission.mutation.Mutator;
import io.javaoperatorsdk.webhook.admission.validation.Validator;

public class AdmissionControllers {

    public static final String ERROR_MESSAGE = "Some error happened";
    public static final String VALIDATION_TARGET_LABEL = "app.kubernetes.io/name";


    // validates if a resource contains the target label
    public static AdmissionController<Ingress> validatingController() {
        return new AdmissionController<>((resource, operation) -> {
            if (resource.getMetadata().getLabels() == null
                    || resource.getMetadata().getLabels().get(VALIDATION_TARGET_LABEL) == null) {
                throw new NotAllowedException("Missing label: " + VALIDATION_TARGET_LABEL);
            }
        });
    }

    public static AsyncAdmissionController<Ingress> asyncValidatingController() {
        return new AsyncAdmissionController<>((resource, operation) -> {
            if (resource.getMetadata().getLabels() == null
                    || resource.getMetadata().getLabels().get(VALIDATION_TARGET_LABEL) == null) {
                throw new NotAllowedException("Missing label: " + VALIDATION_TARGET_LABEL);
            }
        });
    }


    public static AdmissionController<Ingress> errorMutatingController() {
        return new AdmissionController<>((Validator<Ingress>) (resource, operation) -> {
            throw new IllegalStateException(ERROR_MESSAGE);
        });
    }

    public static AdmissionController<Ingress> errorValidatingController() {
        return new AdmissionController<>((Mutator<Ingress>) (resource, operation) -> {
            throw new IllegalStateException(ERROR_MESSAGE);
        });
    }

    public static AsyncAdmissionController<Ingress> errorAsyncMutatingController() {
        return new AsyncAdmissionController<>((AsyncMutator<Ingress>) (resource, operation) -> {
            throw new IllegalStateException(ERROR_MESSAGE);
        });
    }

    public static AsyncAdmissionController<Ingress> errorAsyncValidatingController() {
        return new AsyncAdmissionController<>((Validator<Ingress>) (resource, operation) -> {
            throw new IllegalStateException(ERROR_MESSAGE);
        });
    }
}