const API = "http://localhost:8080/api/v1/disciplinas"

async function listarDisciplinas() {
    try {
        const response = await fetch(API);
        const disciplinas = await response.json();
        const lista = document.getElementById("listaDisciplinas");
        lista.innerHTML = "";
        if (!disciplinas.length) {
            lista.innerHTML = '<li class="list-group-item">Nenhuma disciplina cadastrada</li>';
            return;
        }
        disciplinas.forEach(d => {
            lista.innerHTML += `<li class="list-group-item d-flex justify-content-between align-items-center">
                ${d.nome} <span class="badge bg-success">ID: ${d.id}</span></li>`;
        });
    } catch (e) {
        console.error(e);
    }
}

async function cadastrarDisciplina() {
    const nome = document.getElementById("disciplina").value;
    if (nome.trim() === "") { alert("Digite o nome da disciplina."); return; }
    try {
        const response = await fetch(API, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ nome })
        });
        if (response.ok) {
            alert("Disciplina cadastrada com sucesso!");
            document.getElementById("disciplina").value = "";
            listarDisciplinas();
        } else {
            alert("Erro: " + response.status);
        }
    } catch (e) {
        alert("Erro de conexão.");
    }
}

listarDisciplinas();