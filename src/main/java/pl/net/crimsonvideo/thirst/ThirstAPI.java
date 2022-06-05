package pl.net.crimsonvideo.thirst;

import pl.net.crimsonvideo.thirst.api.IHydrationAPI;

public final class ThirstAPI {
    private static Thirst plugin;
    public IHydrationAPI hydrationAPI;
    ThirstAPI(Thirst plugin) {
        ThirstAPI.plugin = plugin;
        this.hydrationAPI = new Thirst.HydrationAPI();
    }
}
