package br.com.escolares.util;

import java.util.Random;

/**
 * @author vinicius Ribeiro
 *
 * 26 de mai de 2017
 *
 */
public class GeradorRg {
	
    public String gerarRg(){
     String numerosContatenados;
     String numeroGerado;
     
     Random numeroAleatorio = new Random();
      //numeros gerados
     int n1 = numeroAleatorio.nextInt(10);
     int n2 = numeroAleatorio.nextInt(10);
     int n3 = numeroAleatorio.nextInt(10);
     int n4 = numeroAleatorio.nextInt(10);
     int n5 = numeroAleatorio.nextInt(10);
     int n6 = numeroAleatorio.nextInt(10);
     int n7 = numeroAleatorio.nextInt(10);
     int n8 = numeroAleatorio.nextInt(10);
     int n9 = numeroAleatorio.nextInt(10);
      //Conctenando os numeros
     numerosContatenados = String.valueOf(n1) + String.valueOf(n2) +"."+ String.valueOf(n3)  + String.valueOf(n4) +
                           String.valueOf(n5) +"."+String.valueOf(n6) + String.valueOf(n7) +String.valueOf(n8)  +"-"+
                           String.valueOf(n9);
     numeroGerado = numerosContatenados;
     return numeroGerado;
    }
}
