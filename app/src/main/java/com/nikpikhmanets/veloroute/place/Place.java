package com.nikpikhmanets.veloroute.place;

import android.os.Parcel;
import android.os.Parcelable;

public class Place implements Parcelable{

    private String name;
    private String img;
    private String description;
    private Double lat;
    private Double lng;

    public Place() {

    }

    protected Place(Parcel in) {
        name = in.readString();
        img = in.readString();
        lat = in.readDouble();
        lng = in.readDouble();
        description = in.readString();
    }

    public static final Creator<Place> CREATOR = new Creator<Place>() {
        @Override
        public Place createFromParcel(Parcel in) {
            return new Place(in);
        }

        @Override
        public Place[] newArray(int size) {
            return new Place[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(img);
        dest.writeDouble(lat);
        dest.writeDouble(lng);
        dest.writeString(description);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    private String lorpenText = "Aenean venenatis purus augue, sed interdum mi mattis vitae. Etiam ullamcorper ligula ut condimentum tristique. Praesent vehicula massa quis dui vestibulum tempus eget in arcu. Donec suscipit felis vitae purus aliquam euismod. Vestibulum eleifend sit amet metus non iaculis. Duis blandit erat nec risus molestie sodales. Cras iaculis sapien felis, ac fringilla quam pellentesque non. Vestibulum cursus, diam a convallis lacinia, libero eros consequat quam, ac feugiat odio nulla in lorem. Sed rhoncus gravida sem, eu vulputate turpis commodo quis. Nunc fermentum libero arcu, vitae aliquam est sagittis eget. Nulla non ex sed nisi mattis suscipit. Phasellus a posuere libero. Suspendisse potenti. Ut fringilla, metus posuere viverra pulvinar, mauris orci viverra diam, in imperdiet massa sem in velit.\n" +
            "\n" +
            "Curabitur mollis ligula eget sapien convallis rutrum. Curabitur aliquet ligula ut sem condimentum, a ultrices odio vehicula. Sed imperdiet vestibulum erat, a fermentum tellus dictum tincidunt. Aenean malesuada tincidunt ullamcorper. Sed bibendum sed odio ut gravida. Ut finibus, enim vel sollicitudin ultrices, metus lacus elementum tellus, ut euismod diam ipsum et elit. Etiam mattis libero sed nibh ullamcorper, nec euismod eros eleifend. In scelerisque nisl quis velit interdum volutpat. Nulla elementum velit eros. Proin sed porta erat. Vestibulum hendrerit nunc non nisi pulvinar, quis laoreet diam faucibus. Sed dignissim nunc eget dui luctus dapibus. Quisque non neque et metus vehicula vehicula mollis ac sem. Nunc commodo cursus tortor. Nunc ac risus ac arcu volutpat accumsan.\n" +
            "\n" +
            "In commodo mollis purus id placerat. Morbi euismod sem at tellus condimentum lacinia. Integer eu pharetra nunc. Aliquam non ligula ac purus convallis gravida. Ut hendrerit risus accumsan nisl semper cursus. Phasellus viverra eleifend dolor ut dapibus. Vestibulum pulvinar, arcu quis aliquam condimentum, sem nisi congue magna, eu volutpat libero enim in est. Curabitur malesuada orci ornare sollicitudin venenatis. Phasellus rutrum odio eu ipsum ullamcorper varius. Sed ut sollicitudin lacus. Vestibulum vulputate massa eu luctus rhoncus. Nam quis ligula sodales, ultricies metus eget, lobortis orci. Quisque pulvinar, sem id varius porta, velit nisl gravida leo, nec rutrum ligula elit tempus libero. Etiam vulputate tortor diam, at posuere tortor vulputate nec. Nullam diam massa, dapibus ut mi nec, aliquet convallis massa.";
}
