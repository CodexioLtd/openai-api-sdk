package bg.codexio.ai.openai.api.payload.message;

public record MessageResult(
        String message,
        String imageFileId
) {
    public static MessageResult empty() {
        return new MessageResult(
                null,
                null
        );
    }

    public static Builder builder() {
        return new Builder(
                null,
                null
        );
    }

    public record Builder(
            String message,
            String fileId
    ) {
        public Builder withMessage(String message) {
            return new Builder(
                    message,
                    fileId
            );
        }

        public Builder withImageFileId(String fileId) {
            return new Builder(
                    message,
                    fileId
            );
        }

        public MessageResult build() {
            return new MessageResult(
                    message,
                    fileId
            );
        }
    }
}
