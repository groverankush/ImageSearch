package com.ankushgrover.imagesearch.data.model.photo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.ankushgrover.imagesearch.data.model.photosearchmapping.PhotoSearchMap;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(indices = {@Index(value = "search_term_id")  ,@Index(value = {"id", "search_term_id"}, unique = true)}, foreignKeys = @ForeignKey(entity = PhotoSearchMap.class, parentColumns = "id", childColumns = "search_term_id", onDelete = CASCADE))
public class Photo implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int _id;

    @ColumnInfo(name = "search_term_id")
    private long searchTermId;

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("owner")
    @Expose
    private String owner;
    @SerializedName("secret")
    @Expose
    private String secret;
    @SerializedName("server")
    @Expose
    private String server;
    @SerializedName("farm")
    @Expose
    private Integer farm;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("ispublic")
    @Expose
    private Integer ispublic;
    @SerializedName("isfriend")
    @Expose
    private Integer isfriend;
    @SerializedName("isfamily")
    @Expose
    private Integer isfamily;

    public Photo(String id, String secret) {
        this.id = id;
        this.secret = secret;
    }

    protected Photo(Parcel in) {
        _id = in.readInt();
        searchTermId = in.readLong();
        id = in.readString();
        owner = in.readString();
        secret = in.readString();
        server = in.readString();
        if (in.readByte() == 0) {
            farm = null;
        } else {
            farm = in.readInt();
        }
        title = in.readString();
        if (in.readByte() == 0) {
            ispublic = null;
        } else {
            ispublic = in.readInt();
        }
        if (in.readByte() == 0) {
            isfriend = null;
        } else {
            isfriend = in.readInt();
        }
        if (in.readByte() == 0) {
            isfamily = null;
        } else {
            isfamily = in.readInt();
        }
    }

    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public Integer getFarm() {
        return farm;
    }

    public void setFarm(Integer farm) {
        this.farm = farm;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getIspublic() {
        return ispublic;
    }

    public void setIspublic(Integer ispublic) {
        this.ispublic = ispublic;
    }

    public Integer getIsfriend() {
        return isfriend;
    }

    public void setIsfriend(Integer isfriend) {
        this.isfriend = isfriend;
    }

    public Integer getIsfamily() {
        return isfamily;
    }

    public void setIsfamily(Integer isfamily) {
        this.isfamily = isfamily;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public long getSearchTermId() {
        return searchTermId;
    }

    public void setSearchTermId(long searchTermId) {
        this.searchTermId = searchTermId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(_id);
        parcel.writeLong(searchTermId);
        parcel.writeString(id);
        parcel.writeString(owner);
        parcel.writeString(secret);
        parcel.writeString(server);
        if (farm == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(farm);
        }
        parcel.writeString(title);
        if (ispublic == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(ispublic);
        }
        if (isfriend == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(isfriend);
        }
        if (isfamily == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(isfamily);
        }
    }
}
