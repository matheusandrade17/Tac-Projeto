const API = "http://localhost:8080/api/v1/alunos"

async function listarAlunos() {
    // Endpoint de listagem não implementado no backend
    // Lista fica vazia por padrão
    document.getElementById("lista").innerHTML = 
        '<li class="list-group-item text-muted">Use o campo abaixo para cadastrar alunos.</li>';
}

async function cadastrarAluno() {
    const nome = document.getElementById("nome").value;
    const email = document.getElementById("email") ? 
        document.getElementById("email").value : nome.toLowerCase().replace(" ","") + "@academico.com";

    if (nome.trim() === "") { alert("Digite um nome."); return; }

    try {
        const response = await fetch(API, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ nome: nome, email: email })
        });

        if (response.ok) {
            const aluno = await response.json();
            alert("Aluno cadastrado! ID: " + aluno.id);
            document.getElementById("nome").value = "";
        } else {
            alert("Erro ao cadastrar: " + response.status);
        }
    } catch (erro) {
        alert("Erro de conexão com o servidor.");
    }
}

listarAlunos();