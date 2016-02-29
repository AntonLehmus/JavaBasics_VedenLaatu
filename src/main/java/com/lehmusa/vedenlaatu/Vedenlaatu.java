
package com.lehmusa.vedenlaatu;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Vedenlaatu {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("latest_measurements")
    @Expose
    private LatestMeasurements latestMeasurements;
    @SerializedName("colors")
    @Expose
    private Colors colors;

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The slug
     */
    public String getSlug() {
        return slug;
    }

    /**
     * 
     * @param slug
     *     The slug
     */
    public void setSlug(String slug) {
        this.slug = slug;
    }

    /**
     * 
     * @return
     *     The latestMeasurements
     */
    public LatestMeasurements getLatestMeasurements() {
        return latestMeasurements;
    }

    /**
     * 
     * @param latestMeasurements
     *     The latest_measurements
     */
    public void setLatestMeasurements(LatestMeasurements latestMeasurements) {
        this.latestMeasurements = latestMeasurements;
    }

    /**
     * 
     * @return
     *     The colors
     */
    public Colors getColors() {
        return colors;
    }

    /**
     * 
     * @param colors
     *     The colors
     */
    public void setColors(Colors colors) {
        this.colors = colors;
    }

}
