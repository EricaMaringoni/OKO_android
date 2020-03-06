package com.Se4Med.OKO;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class risultatotest_Test {



    @Test
    public void risultatoTest() throws Exception {
        // Given a mocked Context injected into the object under test...

        RisultatoTest myObjectUnderTest = new RisultatoTest();
        String[] stringaRis = {"1","1","1","1","1","1","1","1","1","1","1","1"};

        // ...when the string is returned from the object under test...
        double risultato = myObjectUnderTest.calcolaRisultato(stringaRis);

        int risultato_int = ((int)risultato);
        String risultato_str = risultato_int + "";
        assertTrue(risultato_str.equals("100"));

        int valuta_vista = myObjectUnderTest.valutaVista(risultato);
        String valuta_vista_str = valuta_vista+"";
        assertTrue(valuta_vista_str.equals("3"));

        String descrizione = myObjectUnderTest.getDescrizioneVista(valuta_vista,3,3);

        String descr = "Congratulations, your visual acuity seems good in both eyes.\r\n"
                + "You can read even small print with ease.\r\n"
                + "Feel free to redo this test regularly to monitor your vision. However, " +
                "to verify the health of your eyes, don't hesitate " +
                "to fix an appointment with an eye care professional.\r\n"
                + "";

        assertTrue(descrizione.equals(descr));
    }

}