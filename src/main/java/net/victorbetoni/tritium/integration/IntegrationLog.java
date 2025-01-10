package net.victorbetoni.tritium.integration;

public record IntegrationLog(
        Long time,
        Status status,
        String message
) {

    public enum Status {
        FAILURE("FALHA"), INFO("INFO"), SUCCESS("SUCESSO");

        private String name;

        Status(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }



    public static IntegrationLog failure(String message) {
        return new IntegrationLog(System.currentTimeMillis(), Status.FAILURE, message);
    }

    public static IntegrationLog success(String message) {
        return new IntegrationLog(System.currentTimeMillis(), Status.SUCCESS, message);
    }

    public static IntegrationLog info(String message) {
        return new IntegrationLog(System.currentTimeMillis(), Status.INFO, message);
    }


}
