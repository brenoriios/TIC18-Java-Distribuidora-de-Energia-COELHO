import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

class Cliente {
    private String nome;
    private String cpf;
    private List<Imovel> imoveis;

    public Cliente(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
        this.imoveis = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public List<Imovel> getImoveis() {
        return imoveis;
    }
}

class Imovel {
    private String endereco;
    private double penultimaLeitura;
    private double ultimaLeitura;
    private List<Fatura> faturas;
    
    



    public Imovel(String endereco, double penultimaLeitura, double ultimaLeitura) {
        this.endereco = endereco;
        this.penultimaLeitura = penultimaLeitura;
        this.ultimaLeitura = ultimaLeitura;
        this.faturas = new ArrayList<>();
       
    }

    public String getEndereco() {
        return endereco;
    }

    public double getPenultimaLeitura() {
        return penultimaLeitura;
    }

    public double getUltimaLeitura() {
        return ultimaLeitura;
    }

    public List<Fatura> getFaturas() {
        return faturas;
    }

    public double calcularConsumo() {
        return ultimaLeitura - penultimaLeitura;
    }

   
}
class Pagamento {
    private double valor;
    private Date data;
    private boolean reembolso;

    public Pagamento(double valor) {
        this.valor = valor;
        this.data = new Date();
        this.reembolso = false;
    }

    public double getValor() {
        return valor;
    }

    public Date getData() {
        return data;
    }

    public boolean isReembolso() {
        return reembolso;
    }

    public void setReembolso(boolean reembolso) {
        this.reembolso = reembolso;
    }
}

class Fatura {
    private double penultimaLeitura;
    private double ultimaLeitura;
    private Date dataEmissao;
    private double valorCalculado;
    private boolean quitada;

    private List<Pagamento> pagamentos;

    public Fatura(double penultimaLeitura, double ultimaLeitura, double custoPorKWh) {
        this.penultimaLeitura = penultimaLeitura;
        this.ultimaLeitura = ultimaLeitura;
        this.dataEmissao = new Date();
        this.valorCalculado = calcularValor(custoPorKWh);
        this.quitada = false;
        this.pagamentos = new ArrayList<>();
    }

    private static final double CUSTO_POR_KWH = 10.0; 

    public List<Pagamento> getPagamentos() {
        return pagamentos;
    }

    public void incluirPagamento(double valor) {
        if (!isQuitada()) {
            Pagamento pagamento = new Pagamento(valor);
            pagamentos.add(pagamento);
            verificarQuitacao();
            if (valor > calcularValor(CUSTO_POR_KWH)) {
                double valorReembolso = valor - calcularValor(CUSTO_POR_KWH);
                System.out.println("Reembolso gerado: " + valorReembolso);
                Pagamento reembolso = new Pagamento(valorReembolso);
                reembolso.setReembolso(true);
                pagamentos.add(reembolso);
            }
            System.out.println("Pagamento registrado com sucesso!");
        } else {
            System.out.println("A fatura já está quitada. Não é possível realizar o pagamento.");
        }
    }

    private void verificarQuitacao() {
        double totalPago = pagamentos.stream().mapToDouble(Pagamento::getValor).sum();
        if (totalPago >= calcularValor(CUSTO_POR_KWH) && !isQuitada()) {
            setQuitada(true);
        }
    }

    private double calcularValor(double custoPorKWh) {
        return (ultimaLeitura - penultimaLeitura) * custoPorKWh;
    }

    public Date getDataEmissao() {
        return dataEmissao;
    }

    public double getValorCalculado() {
        return valorCalculado;
    }

    public boolean isQuitada() {
        return quitada;
    }

    public void setQuitada(boolean quitada) {
        this.quitada = quitada;
    }
}


public class App {
    private static final double CUSTO_POR_KWH = 10.0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<Cliente> clientes = new ArrayList<>();

        int escolha;
        do {
            System.out.println("Menu Principal");
            System.out.println("1. Gestão de Clientes");
            System.out.println("2. Gestão de Imóveis");
            System.out.println("3. Menu Faturas");
            System.out.println("4. Menu Pagamentos");
            System.out.println("5. Falhas e Reparos");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            escolha = scanner.nextInt();

            switch (escolha) {
                case 1:
                    menuGestaoClientes(clientes);
                    break;
                case 2:
                    // Implementar menu de gestão de imóveis
                    break;
                case 3:
                    menuFaturas(clientes);
                    break;
                case 4:
                    menuPagamentos(clientes);
                    break;
            
                case 0:
                    System.out.println("Saindo do programa.");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (escolha != 0);
    }

    private static void menuGestaoClientes(List<Cliente> clientes) {
        Scanner scanner = new Scanner(System.in);

        int escolha;
        do {
            System.out.println("\nMenu Gestão de Clientes");
            System.out.println("1. Incluir Cliente");
            System.out.println("2. Consultar Cliente");
            System.out.println("3. Listar Clientes");
            System.out.println("4. Excluir Cliente");
            System.out.println("5. Alterar Cliente");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            escolha = scanner.nextInt();

            switch (escolha) {
                case 1:
                    incluirCliente(clientes);
                    break;
                case 2:
                    consultarCliente(clientes);
                    break;
                case 3:
                    listarClientes(clientes);
                    break;
                case 4:
                    excluirCliente(clientes);
                    break;
                case 5:
                    alterarCliente(clientes);
                    break;
                case 0:
                    System.out.println("Voltando ao Menu Principal.");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (escolha != 0);
    }

    private static void incluirCliente(List<Cliente> clientes) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o nome do cliente: ");
        String nome = scanner.nextLine();
        System.out.print("Digite o CPF do cliente: ");
        String cpf = scanner.nextLine();

        Cliente novoCliente = new Cliente(nome, cpf);
        clientes.add(novoCliente);
        System.out.println("Cliente adicionado com sucesso!");
    }

    private static void consultarCliente(List<Cliente> clientes) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o CPF do cliente a ser consultado: ");
        String cpf = scanner.nextLine();

        for (Cliente cliente : clientes) {
            if (cliente.getCpf().equals(cpf)) {
                System.out.println("Nome: " + cliente.getNome());
                System.out.println("CPF: " + cliente.getCpf());
                List<Imovel> imoveis = cliente.getImoveis();
                if (!imoveis.isEmpty()) {
                    System.out.println("Imóveis do Cliente:");
                    for (Imovel imovel : imoveis) {
                        System.out.println("Endereço: " + imovel.getEndereco());
                        System.out.println("Última Leitura: " + imovel.getUltimaLeitura());
                        System.out.println("Penúltima Leitura: " + imovel.getPenultimaLeitura());
                        System.out.println("Consumo: " + imovel.calcularConsumo());
                        System.out.println();
                    }
                } else {
                    System.out.println("O cliente não possui imóveis cadastrados.");
                }
                return;
            }
        }

        System.out.println("Cliente não encontrado.");
    }

    private static void listarClientes(List<Cliente> clientes) {
        if (clientes.isEmpty()) {
            System.out.println("Não há clientes cadastrados.");
        } else {
            System.out.println("Lista de Clientes:");
            for (Cliente cliente : clientes) {
                System.out.println("Nome: " + cliente.getNome());
                System.out.println("CPF: " + cliente.getCpf());
                System.out.println();
            }
        }
    }

    private static void excluirCliente(List<Cliente> clientes) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o CPF do cliente a ser excluído: ");
        String cpf = scanner.nextLine();

        for (Cliente cliente : clientes) {
            if (cliente.getCpf().equals(cpf)) {
                clientes.remove(cliente);
                System.out.println("Cliente excluído com sucesso!");
                return;
            }
        }

        System.out.println("Cliente não encontrado.");
    }

    private static void alterarCliente(List<Cliente> clientes) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o CPF do cliente a ser alterado: ");
        String cpf = scanner.nextLine();

        for (Cliente cliente : clientes) {
            if (cliente.getCpf().equals(cpf)) {
                System.out.print("Digite o novo nome do cliente: ");
                String novoNome = scanner.nextLine();
                cliente.setNome(novoNome);
                System.out.println("Cliente alterado com sucesso!");
                return;
            }
        }

        System.out.println("Cliente não encontrado.");
    }

    private static void menuFaturas(List<Cliente> clientes) {
        Scanner scanner = new Scanner(System.in);

        int escolha;
        do {
            System.out.println("\nMenu Faturas");
            System.out.println("1. Criar Fatura");
            System.out.println("2. Listar Todas as Faturas");
            System.out.println("3. Listar Faturas em Aberto");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            escolha = scanner.nextInt();

            switch (escolha) {
                case 1:
                    criarFatura(clientes);
                    break;
                case 2:
                    listarTodasFaturas(clientes);
                    break;
                case 3:
                    listarFaturasEmAberto(clientes);
                    break;
                case 0:
                    System.out.println("Voltando ao Menu Principal.");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (escolha != 0);
    }

    private static void criarFatura(List<Cliente> clientes) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o CPF do cliente: ");
        String cpf = scanner.nextLine();

        Cliente cliente = buscarClientePorCPF(clientes, cpf);

        if (cliente != null) {
            System.out.print("Digite o índice do imóvel para o qual deseja gerar a fatura: ");
            int indiceImovel = scanner.nextInt();

            List<Imovel> imoveis = cliente.getImoveis();

            if (indiceImovel >= 0 && indiceImovel < imoveis.size()) {
                Imovel imovelSelecionado = imoveis.get(indiceImovel);

                Fatura novaFatura = new Fatura(imovelSelecionado.getPenultimaLeitura(),
                        imovelSelecionado.getUltimaLeitura(), CUSTO_POR_KWH);


                System.out.println("Fatura criada com sucesso!");
            } else {
                System.out.println("Índice de imóvel inválido.");
            }
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }

    private static void listarTodasFaturas(List<Cliente> clientes) {
        
    }

    private static void listarFaturasEmAberto(List<Cliente> clientes) {
        
    }


    private static Cliente buscarClientePorCPF(List<Cliente> clientes, String cpf) {
        for (Cliente cliente : clientes) {
            if (cliente.getCpf().equals(cpf)) {
                return cliente;
            }
        }
        return null;
    }

    private static void menuPagamentos(List<Cliente> clientes) {
        Scanner scanner = new Scanner(System.in);

        int escolha;
        do {
            System.out.println("\nMenu Pagamentos");
            System.out.println("1. Incluir Pagamento");
            System.out.println("2. Listar Todos os Pagamentos");
            System.out.println("3. Listar Pagamentos de uma Fatura");
            System.out.println("4. Listar Todos os Reembolsos");
            System.out.println("5. Listar Reembolsos de uma Fatura");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            escolha = scanner.nextInt();

            switch (escolha) {
                case 1:
                    incluirPagamento(clientes);
                    break;
                case 2:
                    listarTodosPagamentos(clientes);
                    break;
                case 3:
                    listarPagamentosDeFatura(clientes);
                    break;
                case 4:
                    listarTodosReembolsos(clientes);
                    break;
                case 5:
                    listarReembolsosDeFatura(clientes);
                    break;
                case 0:
                    System.out.println("Voltando ao Menu Principal.");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (escolha != 0);
    }

    private static void incluirPagamento(List<Cliente> clientes) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o CPF do cliente: ");
        String cpf = scanner.nextLine();

        Cliente cliente = buscarClientePorCPF(clientes, cpf);

        if (cliente != null) {
            System.out.print("Digite o índice do imóvel: ");
            int indiceImovel = scanner.nextInt();

            List<Imovel> imoveis = cliente.getImoveis();

            if (indiceImovel >= 0 && indiceImovel < imoveis.size()) {
                Imovel imovel = imoveis.get(indiceImovel);

                List<Fatura> faturas = cliente.getImoveis().get(indiceImovel).getFaturas();
                System.out.print("Digite o índice da fatura: ");
                int indiceFatura = scanner.nextInt();

                if (indiceFatura >= 0 && indiceFatura < faturas.size()) {
                    Fatura fatura = faturas.get(indiceFatura);

                    System.out.print("Digite o valor do pagamento: ");
                    double valorPagamento = scanner.nextDouble();

                    fatura.incluirPagamento(valorPagamento);
                } else {
                    System.out.println("Índice de fatura inválido.");
                }
            } else {
                System.out.println("Índice de imóvel inválido.");
            }
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }

    private static void listarTodosPagamentos(List<Cliente> clientes) {
        for (Cliente cliente : clientes) {
            for (Imovel imovel : cliente.getImoveis()) {
                for (Fatura fatura : imovel.getFaturas()) {
                    for (Pagamento pagamento : fatura.getPagamentos()) {
                        System.out.println("CPF: " + cliente.getCpf());
                        System.out.println("Endereço: " + imovel.getEndereco());
                        System.out.println("Fatura: " + fatura.getDataEmissao());
                        System.out.println("Data do Pagamento: " + pagamento.getData());
                        System.out.println("Valor do Pagamento: " + pagamento.getValor());
                        System.out.println("Reembolso: " + pagamento.isReembolso());
                        System.out.println();
                    }
                }
            }
        }
    }

    private static void listarPagamentosDeFatura(List<Cliente> clientes) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o CPF do cliente: ");
        String cpf = scanner.nextLine();

        Cliente cliente = buscarClientePorCPF(clientes, cpf);

        if (cliente != null) {
            System.out.print("Digite o índice do imóvel: ");
            int indiceImovel = scanner.nextInt();

            List<Imovel> imoveis = cliente.getImoveis();

            if (indiceImovel >= 0 && indiceImovel < imoveis.size()) {
                Imovel imovel = imoveis.get(indiceImovel);

                List<Fatura> faturas = imovel.getFaturas();
                System.out.print("Digite o índice da fatura: ");
                int indiceFatura = scanner.nextInt();

                if (indiceFatura >= 0 && indiceFatura < faturas.size()) {
                    Fatura fatura = faturas.get(indiceFatura);

                    for (Pagamento pagamento : fatura.getPagamentos()) {
                        System.out.println("Data do Pagamento: " + pagamento.getData());
                        System.out.println("Valor do Pagamento: " + pagamento.getValor());
                        System.out.println("Reembolso: " + pagamento.isReembolso());
                        System.out.println();
                    }
                } else {
                    System.out.println("Índice de fatura inválido.");
                }
            } else {
                System.out.println("Índice de imóvel inválido.");
            }
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }

    private static void listarTodosReembolsos(List<Cliente> clientes) {
        for (Cliente cliente : clientes) {
            for (Imovel imovel : cliente.getImoveis()) {
                for (Fatura fatura : imovel.getFaturas()) {
                    for (Pagamento pagamento : fatura.getPagamentos()) {
                        if (pagamento.isReembolso()) {
                            System.out.println("CPF: " + cliente.getCpf());
                            System.out.println("Endereço: " + imovel.getEndereco());
                            System.out.println("Fatura: " + fatura.getDataEmissao());
                            System.out.println("Data do Reembolso: " + pagamento.getData());
                            System.out.println("Valor do Reembolso: " + pagamento.getValor());
                            System.out.println();
                        }
                    }
                }
            }
        }
    }

    private static void listarReembolsosDeFatura(List<Cliente> clientes) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o CPF do cliente: ");
        String cpf = scanner.nextLine();

        Cliente cliente = buscarClientePorCPF(clientes, cpf);

        if (cliente != null) {
            System.out.print("Digite o índice do imóvel: ");
            int indiceImovel = scanner.nextInt();

            List<Imovel> imoveis = cliente.getImoveis();

            if (indiceImovel >= 0 && indiceImovel < imoveis.size()) {
                Imovel imovel = imoveis.get(indiceImovel);

                List<Fatura> faturas = imovel.getFaturas();
                System.out.print("Digite o índice da fatura: ");
                int indiceFatura = scanner.nextInt();

                if (indiceFatura >= 0 && indiceFatura < faturas.size()) {
                    Fatura fatura = faturas.get(indiceFatura);

                    for (Pagamento pagamento : fatura.getPagamentos()) {
                        if (pagamento.isReembolso()) {
                            System.out.println("Data do Reembolso: " + pagamento.getData());
                            System.out.println("Valor do Reembolso: " + pagamento.getValor());
                            System.out.println();
                        }
                    }
                } else {
                    System.out.println("Índice de fatura inválido.");
                }
            } else {
                System.out.println("Índice de imóvel inválido.");
            }
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }

}