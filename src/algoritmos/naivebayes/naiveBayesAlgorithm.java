package algoritmos.naivebayes;

import java.util.ArrayList;
import java.util.List;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class naiveBayesAlgorithm {

    //Weka
    private DataSource arquivo;
    private Instances dados;
    private NaiveBayes naive;

    //Variaveis
    private int numAtributoAlvo;
    private int numValorAlvo;

    public naiveBayesAlgorithm(String url) {
        System.out.println("NAIVE BAYES LOGS(INICIO)");

        System.out.println("ARQUIVO ESCOLHIDO COM SUCESSO: " + this.escolherArquivo(url));

        //Padrao
        this.setNumAtributoAlvo(0);
        this.setNumValorAlvo(0);

    }

    public boolean escolherArquivo(String caminhoUrl) {
        boolean arquivoEscolhidoComSucesso = false;
        try {
            arquivo = new DataSource(caminhoUrl);
            dados = arquivo.getDataSet();
            if (arquivo != null && dados != null) {
                arquivoEscolhidoComSucesso = true;
            }
        } catch (RuntimeException e) {
            arquivoEscolhidoComSucesso = false;
        } catch (Exception e) {
            e.printStackTrace();
            arquivoEscolhidoComSucesso = false;
        }

        return arquivoEscolhidoComSucesso;
    }

    public void setNumAtributoAlvo(int num) {
        numAtributoAlvo = num;
        dados.setClassIndex(num);
    }

    public void setNumValorAlvo(int num) {
        numValorAlvo = num;
        //set
    }

    public int getAtributoVariavelAlvo() {
        return dados.classIndex();
    }

    public List<String> getListaNomesAtributos(boolean comVariavelAlvo) {
        List<String> listaNomesAtributos = new ArrayList<>();

        for (int i = 0; i < dados.numAttributes(); i++) {
            listaNomesAtributos.add(dados.attribute(i).name());
        }
        if (!comVariavelAlvo) {
            listaNomesAtributos.remove(getAtributoVariavelAlvo());
        }

        return listaNomesAtributos;
    }

    public List<String> getListaNomesValores(int numeroAtributo, boolean comVariavelAlvo) {
        List<String> listaNomesValores = new ArrayList<>();

        if (comVariavelAlvo) {
            for (int j = 0; j < dados.attribute(numeroAtributo).numValues(); j++) {
                listaNomesValores.add(dados.attribute(numeroAtributo).value(j));
            }
        } else {
            if (numeroAtributo >= numAtributoAlvo) {
                for (int j = 0; j < dados.attribute(numeroAtributo + 1).numValues(); j++) {
                    listaNomesValores.add(dados.attribute(numeroAtributo + 1).value(j));
                }
            } else {
                for (int j = 0; j < dados.attribute(numeroAtributo).numValues(); j++) {
                    listaNomesValores.add(dados.attribute(numeroAtributo).value(j));
                }
            }
        }
        return listaNomesValores;
    }

    public boolean criarModeloNaive() throws Exception {
        boolean modeloNaiveCriado = false;
        naive = new NaiveBayes();

        naive.buildClassifier(dados);
        if (naive != null) {
            modeloNaiveCriado = true;
        }
        return modeloNaiveCriado;
    }
}
