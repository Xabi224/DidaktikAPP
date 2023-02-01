package com.elorrieta.didaktikapp.galdetegi;

import com.elorrieta.didaktikapp.R;

public class QuestionAnswer {

    public static int[] question ={
            R.string.galdera1,
            R.string.galdera2,
            R.string.galdera3,
            R.string.galdera4,
    };

    public static int[][] choices = {
            {R.string.aukera1a,R.string.aukera1b,R.string.aukera1c},
            {R.string.aukera2a,R.string.aukera2b,R.string.aukera2c},
            {R.string.aukera3a,R.string.aukera3b,R.string.aukera3c},
            {R.string.aukera4a,R.string.aukera4b,R.string.aukera4c}
    };

    public static int[] correctAnswers = {
            R.string.erantzuna1,
            R.string.erantzuna2,
            R.string.erantzuna3,
            R.string.erantzuna4
    };

}
