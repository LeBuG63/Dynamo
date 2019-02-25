package iut.ipi.runnergame.Game;

import android.os.Parcel;
import android.os.Parcelable;

public class GameOverDataBundle implements Parcelable {
    public static final Parcelable.Creator<GameOverDataBundle> CREATOR = new Parcelable.Creator<GameOverDataBundle>() {
        @Override
        public GameOverDataBundle createFromParcel(Parcel source) {
            return new GameOverDataBundle(source);
        }

        @Override
        public GameOverDataBundle[] newArray(int size) {
            return new GameOverDataBundle[size];
        }
    };

    private String timer;
    private int distance;

    public GameOverDataBundle(Parcel in) {
        timer = in.readString();
        distance = in.readInt();
    }

    public GameOverDataBundle(String timer, int distance) {
        this.timer = timer;
        this.distance = distance;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(timer);
        dest.writeInt(distance);
    }

    public String getTimer() {
        return timer;
    }

    public int getDistance() {
        return distance;
    }
}
