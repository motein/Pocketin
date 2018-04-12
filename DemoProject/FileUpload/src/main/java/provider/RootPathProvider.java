package provider;

public class RootPathProvider implements IRootPathProvider {

    private final String path;

    public RootPathProvider(String path) {
        this.path = path;
    }

    @Override
    public String getRootPath() {
        return path;
    }
}