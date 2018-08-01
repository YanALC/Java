import java.util.ArrayList;

public class Controle {

	public static ArrayList<Integer> saida = new ArrayList<Integer>();
	
	public static void limite(Porta a,ArrayList<Integer> s) { // verifica limite de tempo de aberta ou fechada
		if(saida.size()<2) return;
		int aux = saida.get(saida.size()-1);
		int aux2 = saida.get(saida.size()-2);
		if (((a.anterior==Movimento.ABRINDO && a.atual==Movimento.ABRINDO) && aux==5 && aux2==5)||
				((a.anterior==Movimento.FECHANDO && a.atual==Movimento.FECHANDO) && aux==0 && aux2==0)) {
			a.anterior=Movimento.PARADA;
			if(a.atual==Movimento.ABRINDO) a.atual=Movimento.FECHANDO; 
			else a.atual=Movimento.ABRINDO;
		}
	}

	public static void executar(Porta PORTA, String entrada) {
		int tempo = 0;
		char aux;
		while (!entrada.isEmpty()) {
			aux = entrada.charAt(0);
			switch (aux) {
				case('P'): // botao pressionado
				{
					if (tempo > 0) {
						if(saida.get(tempo-1)<5 && saida.get(tempo-1)>0) { 
							if (PORTA.atual != Movimento.PARADA) { // porta para se estiver em movimento
								saida.add(saida.get(tempo - 1));
								PORTA.atual = Movimento.PARADA;
							} else if (PORTA.anterior == Movimento.FECHANDO) { // senao, continua o anterior
								saida.add(saida.get(tempo - 1) - 1);
								PORTA.anterior = PORTA.atual;
								PORTA.atual = Movimento.FECHANDO;
							} else {
								saida.add(saida.get(tempo - 1) + 1);
								PORTA.anterior = PORTA.atual;
								PORTA.atual = Movimento.ABRINDO;
							}
						} else if (saida.get(tempo-1)==5) { // se esta aberta, fechar
							saida.add(saida.get(tempo - 1) - 1);
							PORTA.anterior = PORTA.atual;
							PORTA.atual = Movimento.FECHANDO;
						} else { //senão, abrir
							saida.add(saida.get(tempo - 1) + 1);
							PORTA.anterior = PORTA.atual;
							PORTA.atual = Movimento.ABRINDO;
						}
					} else { //inicio
						saida.add(1);
						PORTA.atual = Movimento.ABRINDO;
					}
					break;
				}
				case('O'): // obstaculo
				{
					if (tempo > 0) {
						if (PORTA.atual != Movimento.PARADA) { // se achar obstaculo reverte direcao
							if (PORTA.atual == Movimento.ABRINDO) {
								saida.add(saida.get(tempo - 1) - 1);
								PORTA.anterior = PORTA.atual;
								PORTA.atual = Movimento.FECHANDO;
							} else {
								saida.add(saida.get(tempo - 1) + 1);
								PORTA.anterior = PORTA.atual;
								PORTA.atual = Movimento.ABRINDO;
							}
						}
					}
					break;
				}
				case ('.'): // continua
				{
					if (tempo > 0) { // continua a acao atual
						if (PORTA.atual == Movimento.FECHANDO) {
							saida.add(saida.get(tempo - 1) - 1);
							PORTA.anterior = PORTA.atual;
						} else if (PORTA.atual == Movimento.ABRINDO){
							saida.add(saida.get(tempo - 1) + 1);
							PORTA.anterior = PORTA.atual;
						} else {
							saida.add(saida.get(tempo-1));
						}
					}
					else //inicio
						saida.add(0);
					break;
				}
				default:
					break;
			}
			if (saida.get(tempo)<0)
				saida.set(tempo,0);
			else if (saida.get(tempo)>5)
				saida.set(tempo, 5);
			
			limite(PORTA, saida);
			tempo++;
			entrada=entrada.substring(1, entrada.length());
		}
		
	}

	public static void main(String[] args) {
		Porta A = new Porta(Movimento.PARADA);
		System.out.println("Operação: ");
		//Scanner ent = new Scanner(System.in);
		//String e = ent.nextLine();
		//executar(A, e);
		executar(A,"..P...O.....");
		System.out.println(saida);
		saida.clear();
		A = new Porta(Movimento.PARADA);
		executar(A,".P....");
		System.out.println(saida);
		saida.clear();
		A = new Porta(Movimento.PARADA);
		executar(A,"...P.P...P..");
		System.out.println(saida);
		saida.clear();
		A = new Porta(Movimento.PARADA);
		executar(A,"P..O..");
		System.out.println(saida);
		saida.clear();
		A = new Porta(Movimento.PARADA);
		executar(A,"P..P...PO...");
		System.out.println(saida);
		saida.clear();
		A = new Porta(Movimento.PARADA);
		executar(A,"P...O.O.P...P..");
		System.out.println(saida);
		saida.clear();
		A = new Porta(Movimento.PARADA);
		executar(A,"P.....P.P..P...");
		System.out.println(saida);
	}
}
