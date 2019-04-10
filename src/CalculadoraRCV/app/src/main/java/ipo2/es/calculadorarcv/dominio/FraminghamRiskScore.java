package ipo2.es.calculadorarcv.dominio;

import android.util.Log;

/**
 * This is the server logic for the Framingham Risk Score Calculator.
 * It calculates the Framingham Risk Score, a sex-specific algorithm used
 * to estimate the 10-year cardiovascular risk of an individual.
 *
 *  Andrey Kuznetsov, Jan 2015
 *
 *  Sacado de:
 *  https://github.com/ankuznetsov/Framingham_risk_score/blob/master/server.R
 */

public final class FraminghamRiskScore {

    private static int scoreMen(int edad, boolean fumador, boolean diabetes,
                                int colHDL, int colTotal, boolean hvi, int pas){
        int points = 0;

        /*---------------------------------------
        /               Edad hombres
        /--------------------------------------*/
        if(edad < 30) points = points - 2;
        else if(edad == 31) points = points - 1;
        else if(edad == 32 || edad == 33) points = points + 0;
        else if(edad == 34) points = points + 1;
        else if(edad == 35 || edad == 36 ) points = points + 2;
        else if(edad == 37 || edad == 38 ) points = points + 3;
        else if(edad == 39 ) points = points + 4;
        else if(edad == 40 || edad == 41 ) points = points + 5;
        else if(edad == 42 || edad == 43 ) points = points + 6;
        else if(edad == 44 || edad == 45 ) points = points + 7;
        else if(edad == 46 || edad == 47 ) points = points + 8;
        else if(edad == 48 || edad == 49 ) points = points + 9;
        else if(edad == 50 || edad == 51 ) points = points + 10;
        else if(edad >= 52 && edad <= 54 ) points = points + 11;
        else if(edad == 55 || edad == 56 ) points = points + 12;
        else if(edad >= 57 && edad <= 59 ) points = points + 13;
        else if(edad == 60 || edad == 61 ) points = points + 14;
        else if(edad >= 62 && edad <= 64 ) points = points + 15;
        else if(edad >= 65 && edad <= 67 ) points = points + 16;
        else if(edad >= 68 && edad <= 70 ) points = points + 17;
        else if(edad >= 71 && edad <= 73 ) points = points + 18;
        else if(edad >= 74) points = points +19;


        /*---------------------------------------
        /       PRESIÓN ARTERIAL SISTÓLICA
        /--------------------------------------*/
        points += get_pas_score(pas);

        /*---------------------------------------
        /       COLESTEROL  LDL (malo)(mg/dl)
        /--------------------------------------*/
        points += get_colLDL_score(colTotal);

        /*---------------------------------------
        /       COLESTEROL HDL (bueno)(mg/dl)
        /--------------------------------------*/
        if (colHDL == 0) points+=1;
        else points += get_colHDL_score(colHDL);

        /*---------------------------------------
        /               OTROS FACTORES
        /--------------------------------------*/
        if(fumador) points+=4;
        if(diabetes) points += 3;
        if(hvi) points += 9;



        return points;
    }

    private static  int scoreWomen(int edad, boolean fumador, boolean diabetes,
                                   int colHDL, int colTotal, boolean hvi, int pas){

        int points = 0;
        
        /*---------------------------------------
        /               Edad mujeres
        /--------------------------------------*/
        if(edad <= 41){
            if(edad < 30) points = points - 12;
            else if(edad == 31) points = points - 11;
            else if(edad == 32) points = points - 9;
            else if(edad == 33) points = points - 8;
            else if(edad == 34) points = points - 6;
            else if(edad == 35) points = points - 5;
            else if(edad == 36) points = points - 4;
            else if(edad == 37) points = points - 3;
            else if(edad == 38) points = points - 2;
            else if(edad == 39) points = points - 1;
            else if(edad == 40) points = points - 0;
            else if(edad == 41) points = points + 1;
        }
        else{
            if(edad == 42 || edad == 43 ) points = points + 2;
            else if(edad == 44) points = points + 3;
            else if(edad == 45 || edad == 46 ) points = points + 4;
            else if(edad == 47 || edad == 48 ) points = points + 5;
            else if(edad == 49 || edad == 50 ) points = points + 6;
            else if(edad == 51 || edad == 52 ) points = points + 7;
            else if(edad <= 55) points = points + 8;
            else if(edad <= 60) points = points + 9;
            else if(edad <= 67) points = points + 10;
            else points = points + 11;
        }

        /*---------------------------------------
        /       PRESIÓN ARTERIAL SISTÓLICA
        /--------------------------------------*/
        points += get_pas_score(pas);

        /*---------------------------------------
        /       COLESTEROL LDL (malo)(mg/dl)
        /--------------------------------------*/
        points += get_colLDL_score(colTotal);

        /*---------------------------------------
        /       COLESTEROL HDL (bueno)(mg/dl)
        /--------------------------------------*/
        if (colHDL == 0) points+=1;
        else points += get_colHDL_score(colHDL);


        /*---------------------------------------
        /               OTROS FACTORES
        /--------------------------------------*/
        if(fumador) points+=4;
        if(diabetes) points += 6;
        if(hvi) points += 9;


        return points;

    }

    private static int get_pas_score(int pas){
        //Presión arterial sistólica
        int score = 0;
        if(pas<=104) score+=-2;
        else if(pas<=112) score+=-1;
        else if(pas<=120) score+=0;
        else if(pas<=129) score+=1;
        else if(pas<=139) score+=2;
        else if(pas<=149) score+=3;
        else if(pas<=160) score+=4;
        else if(pas<=172) score+=5;
        else score += 6;


        return score;
    }

    private static int get_colLDL_score(int col){
        int score = 0;
        if(col<=151) score+=-3;
        else if(col<=166) score+=-2;
        else if(col<=182) score+=-1;
        else if(col<=199) score+=0;
        else if(col<=219) score+=1;
        else if(col<=239) score+=2;
        else if(col<=262) score+=3;
        else if(col<=288) score+=4;
        else if(col<=315) score+=5;
        else score += 6;


        return score;
    }

    private static int get_colHDL_score(int col){
        int score = 0;
        if(col<=151) score+=-3;
        else if(col<=166) score+=-2;
        else if(col<=182) score+=-1;
        else if(col<=199) score+=0;
        else if(col<=219) score+=1;
        else if(col<=239) score+=2;
        else if(col<=262) score+=3;
        else if(col<=288) score+=4;
        else if(col<=315) score+=5;
        else score += 6;


        return score;
    }

    private static int scoreToRisk(int score){
        int risk = 0;

        if(score <=1) risk = 1;
        else if(score <= 4) risk = 2;
        else if(score <= 6) risk = 3;
        else if(score <= 8) risk = 4;
        else{
            if(score < 17){
                if(score ==9) risk = 5;
                else if(score <= 11) risk = 6;
                else if(score ==12) risk = 7;
                else if(score ==13) risk = 8;
                else if(score ==14) risk = 9;
                else if(score ==15) risk = 10;
                else  risk = 12;
            }
            else if (score <=24){
                if(score ==17) risk = 13;
                else if(score == 18) risk = 14;
                else if(score ==19) risk = 16;
                else if(score ==20) risk = 18;
                else if(score ==21) risk = 19;
                else if(score ==22) risk = 21;
                else if(score ==23) risk = 23;
                else risk = 25;
            }
            else{
                if(score ==25) risk = 27;
                else if(score == 26) risk = 29;
                else if(score ==27) risk = 31;
                else if(score ==28) risk = 33;
                else if(score ==29) risk = 36;
                else if(score ==30) risk = 38;
                else if(score ==31) risk = 40;
                else risk = 42;
            }
        }
        return risk;
    }

    public static int getRiskScore(char genero, int edad, boolean fumador, boolean diabetes,
                                   int colHDL, int colTotal, boolean hvi, int pas){
        int score= 0;
        if(genero == 'm'){ //mujer
            score = scoreMen(edad, fumador, diabetes, colHDL,colTotal, hvi, pas);

        }else{ //hombre
            score = scoreWomen(edad, fumador, diabetes, colHDL,colTotal, hvi, pas);
        }
        return scoreToRisk(score);
    }
}
