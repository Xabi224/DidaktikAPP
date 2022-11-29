package com.elorrieta.didaktikapp.puzzle;


import androidx.appcompat.widget.AppCompatImageView;



import android.content.Context;

public class PiezaPuzzle extends AppCompatImageView {
    public int xCoord;
    public int yCoord;
    public int pieceWidth;
    public int pieceHeight;
    public boolean canMove = true;

        public PiezaPuzzle(Context context) {super(context);}
    }


