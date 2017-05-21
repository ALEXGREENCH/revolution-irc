package io.mrarm.irc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.mrarm.chatlib.ChatApi;

public class ServerConnectionInfo {

    private UUID mUUID;
    private String mName;
    private List<String> mChannels;
    private ChatApi mApi;
    private boolean mExpandedInDrawer = true;
    private List<ChannelListChangeListener> mChannelsListeners = new ArrayList<>();

    public ServerConnectionInfo(UUID uuid, String name, ChatApi api) {
        mUUID = uuid;
        mName = name;
        mApi = api;
        api.getJoinedChannelList((List<String> channels) -> {
            setChannels(channels);
        }, null);
    }

    public UUID getUUID() {
        return mUUID;
    }

    public String getName() {
        return mName;
    }

    public ChatApi getApiInstance() {
        return mApi;
    }

    public List<String> getChannels() {
        return mChannels;
    }

    public void setChannels(List<String> channels) {
        mChannels = channels;
        for (ChannelListChangeListener listener : mChannelsListeners) {
            listener.onChannelListChanged(this, channels);
        }
    }

    public boolean isExpandedInDrawer() {
        return mExpandedInDrawer;
    }

    public void setExpandedInDrawer(boolean expanded) {
        mExpandedInDrawer = expanded;
    }

    public void addOnChannelListChangeListener(ChannelListChangeListener listener) {
        mChannelsListeners.add(listener);
    }

    public void removeOnChannelListChangeListener(ChannelListChangeListener listener) {
        mChannelsListeners.remove(listener);
    }

    public interface ChannelListChangeListener {

        void onChannelListChanged(ServerConnectionInfo connection, List<String> newChannels);

    }

}
