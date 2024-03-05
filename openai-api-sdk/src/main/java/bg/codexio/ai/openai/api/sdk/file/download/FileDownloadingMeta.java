package bg.codexio.ai.openai.api.sdk.file.download;

import java.io.File;

public record FileDownloadingMeta(
        String fileId,
        String fileName,
        File targetFolder
) {

    public static Builder builder() {
        return new Builder(
                null,
                null,
                null
        );
    }

    public record Builder(
            String fileId,
            String fileName,
            File targetFolder
    ) {
        public Builder withFileId(String fileId) {
            return new Builder(
                    fileId,
                    fileName,
                    targetFolder
            );
        }

        public Builder withFileName(String fileName) {
            return new Builder(
                    fileId,
                    fileName,
                    targetFolder
            );
        }

        public Builder withTargetFolder(File targetFolder) {
            return new Builder(
                    fileId,
                    fileName,
                    targetFolder
            );
        }

        public FileDownloadingMeta build() {
            return new FileDownloadingMeta(
                    fileId,
                    fileName,
                    targetFolder
            );
        }
    }
}