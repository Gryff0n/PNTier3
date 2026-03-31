package ordre;

public interface EcouteurOrdre {

    void setAbonnement(LanceurOrdre lanceurOrdre);

    void traiter(TypeOrdre typeOrdre);

}
