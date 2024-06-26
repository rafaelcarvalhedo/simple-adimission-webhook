package br.carvalhedo.admission;

import io.fabric8.kubernetes.api.model.networking.v1.Ingress;
import io.javaoperatorsdk.webhook.admission.AdmissionController;
import io.javaoperatorsdk.webhook.admission.AsyncAdmissionController;

import jakarta.inject.Named;
import jakarta.inject.Singleton;

public class AdmissionControllerConfig {

    public static final String VALIDATING_CONTROLLER = "validatingController";
    public static final String ASYNC_VALIDATING_CONTROLLER = "asyncValidatingController";

    @Singleton
    @Named(VALIDATING_CONTROLLER)
    public AdmissionController<Ingress> validatingController() {
        return AdmissionControllers.validatingController();
    }

    @Singleton
    @Named(ASYNC_VALIDATING_CONTROLLER)
    public AsyncAdmissionController<Ingress> asyncValidatingController() {
        return AdmissionControllers.asyncValidatingController();
    }
}