/**
 *  Programmer: Sabas Sanchez
 *  File: Superhero.java
 *  Date: 3/4/2020
 *  Project 3 - Superheroes
 *
 *  Represents a superheroes with name, power and image name to be used in a quiz app.
 */

package edu.miracosta.cs134.superheroes.model;

public class Superhero {

    private static final String DIRECTORY = "superheroes";

    private String mName;
    private String mSuperpower;
    private String mOneThing;
    private String mFileName;


    /***
     * Instantiate a new <code>Superhero</code> given its name, superpower, and one thig to know.
     * @param name The name of the <code>Superhero</code>
     * @param superpower The superpower the <code>Superhero</code> possesses.
     * @param oneThing The one thing you should know about this <code>Superhero</code>
     */
    public Superhero(String name, String superpower, String oneThing, String fileName)
    {
        mName = name;
        mSuperpower = superpower;
        mOneThing = oneThing;
        mFileName = fileName;
    }

    /***
     * Gets the name of the Superhero
     * @return The name of the Superhero
     */
    public String getName() {
        return mName;
    }

    /***
     * Gets the super power of the Superhero
     * @return The super power of the Superhero
     */
    public String getSuperpower() {
        return mSuperpower;
    }

    /***
     * Gets the one thing you should know about the Superhero
     * @return the one thing you should know about the Superhero
     */
    public String getOneThing() {
        return mOneThing;
    }

    /***
     * Gets the file name of the Superhero image
     * @return The file name of the Superhero image
     */
    public String getFileName() {
        return mFileName;
    }

    public boolean equals(Object other)
    {
        if(other == null || this.getClass() != other.getClass()) return false;

        Superhero superhero = (Superhero) other;

        if(!mName.equals(superhero.mName)) return false;
        if(!mSuperpower.equals(superhero.mSuperpower)) return false;
        if(!getOneThing().equals(superhero.mOneThing)) return false;
        return mFileName.equals(superhero.mFileName);
    }

    /***
     * Generates a text based representation of this Superhero
     * @return A text based representation of this Superhero
     */
    public String toString()
    {
        return "Superhero{ Name= \" " + mName + "\", Superpower= \""
                + mSuperpower + "\", Onething=\"" + mOneThing +  ", FileName= \""
                + mFileName + "\" }";
    }
}
