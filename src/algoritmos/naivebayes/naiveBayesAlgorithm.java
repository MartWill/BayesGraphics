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

    public String getAtributoVariavelAlvoString() {
        return getListaNomesAtributos(true).get(dados.classIndex());
    }

    public String getAtributoValorAlvo() {
        return getListaNomesValores(dados.classIndex(), true).get(numValorAlvo);
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

    public List<Double> pegandoListaValorProb(int numeroDoAtributo) {
        List<Double> listaDeValores = new ArrayList<>();

        for (int i = 0; i < getListaNomesValores(numeroDoAtributo, false).size(); i++) {
            if (numeroDoAtributo < numAtributoAlvo) {
                listaDeValores.add((double) dados.attributeStats(numeroDoAtributo).nominalCounts[i] / dados.size() * 100);
            } else {
                listaDeValores.add((double) dados.attributeStats(numeroDoAtributo + 1).nominalCounts[i] / dados.size() * 100);
            }
        }

        return listaDeValores;
    }

    public List<Double> pegarListValoresVariavelAlvo() {
        List<Double> listaDeValores = new ArrayList<>();

        for (int i = 0; i < getListaNomesValores(numAtributoAlvo, true).size(); i++) {
            listaDeValores.add((double) dados.attributeStats(numAtributoAlvo).nominalCounts[i] / dados.size() * 100);
        }

        return listaDeValores;
    }

    public List<Double> pegarListaValorNaive(int numeroDeAtributos) {
        List<Double> listaValoresProb = new ArrayList<>();

        for (int i = 0; i < getListaNomesValores(numeroDeAtributos, false).size(); i++) {
            listaValoresProb.add(naive.getConditionalEstimators()[numeroDeAtributos][numValorAlvo].getProbability(i) * 100);
        }

        return listaValoresProb;
    }

    public List<Double> pegarListaDiffAtributos(int numeroDoAtributo) {
        List<Double> listaDiffAtributo = new ArrayList<>();

        for (int i = 0; i < pegandoListaValorProb(numeroDoAtributo).size(); i++) {
            listaDiffAtributo.add(pegarListaValorNaive(numeroDoAtributo).get(i) - pegandoListaValorProb(numeroDoAtributo).get(i));
        }

        return listaDiffAtributo;
    }

    public List<Double> pegarListaDiffGeral(int numeroDoAtributo) {
        List<Double> listaDiffAtributo = new ArrayList<>();
        Double aux = new Double(0);

        for (int i = 0; i < pegandoListaValorProb(numeroDoAtributo).size(); i++) {
            aux += Math.abs(pegarListaValorNaive(numeroDoAtributo).get(i) - pegandoListaValorProb(numeroDoAtributo).get(i));
        }

        listaDiffAtributo.add(aux);
        return listaDiffAtributo;
    }

    //nome do arquivo, n instancias, n atributos, atributo e valor
    public String getNomeArquivo() {
        return this.dados.relationName();
    }

    public int getNInstanciaArquivo() {
        return this.dados.size();
    }

    public int getNAtributoArquivo() {
        return this.getListaNomesAtributos(true).size();
    }

}
