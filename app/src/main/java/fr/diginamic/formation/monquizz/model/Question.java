package fr.diginamic.formation.monquizz.model;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class Question implements Parcelable {

    public int id;
    public String intitule, url_img, author;
    public ArrayList<String> propositions;
    public String bonneReponse;
    private int nbReponse;
    public String userAnswer;

    public Question(String intitule, int nbreponse) {
        this.intitule = intitule;
        propositions = new ArrayList<> ();
        nbReponse = nbreponse;
    }

    protected Question(Parcel in) {
        id = in.readInt();
        intitule = in.readString();
        propositions = in.createStringArrayList();
        bonneReponse = in.readString();
        nbReponse = in.readInt();
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public boolean verifierReponse(String verifReponse) {
        return bonneReponse.equals(verifReponse);
    }
    public void addProposition(String nouvelleproposition) {
        propositions.add(nouvelleproposition);
    }
    public String getIntitule() {
        return intitule;
    }
    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }
    public ArrayList<String> getPropositions() {
        return propositions;
    }
    public void setPropositions(ArrayList<String> propositions) {
        this.propositions = propositions;
    }
    public String getBonneReponse() {
        return bonneReponse;
    }
    public void setBonneReponse(String bonneReponse) {
        this.bonneReponse = bonneReponse;
    }
    public int getNbReponse(){
        return nbReponse;
    }

    public String getUrl_img(){ return url_img; }
    public void setUrl_img(String urlImg){ this.url_img = urlImg; }
    public String getAuthor(){ return author; }
    public void setAuthor(String author){ this.author = author; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(intitule);
        dest.writeStringList(propositions);
        dest.writeString(bonneReponse);
        dest.writeInt(nbReponse);
    }
}

    /**
     * A simple {@link Fragment} subclass.
     * Activities that contain this fragment must implement the
     * {@link OnFragmentInteractionListener} interface
     * to handle interaction events.
     * Use the {@link PlayFragment#newInstance} factory method to
     * create an instance of this fragment.
     */

