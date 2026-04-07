package ordre;

import facadeLudotheque.CategorieNotFoundException;

public interface EcouteurOrdre {

    void setAbonnement(LanceurOrdre lanceurOrdre);

    void traiter(TypeOrdre typeOrdre);

}
