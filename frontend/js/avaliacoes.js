const API = "http://localhost:8080/api/v1/avaliacoes"

async function listarAvaliacoes() {
    try {
        const response = await fetch(API);
        const avaliacoes = await response.json();
        const lista = document.getElementById("listaAvaliacoes");
        lista.innerHTML = "";
        if (!avaliacoes.length) {
            lista.innerHTML = '<li class="list-group-item">Nenhuma avaliação registrada</li>';
            return;
        }
        avaliacoes.forEach(a => {
            lista.innerHTML += `<li class="list-group-item d-flex justify-content-between align-items-center">
                ${a.nomeAluno} <span class="badge bg-warning text-dark">Nota: ${a.nota}</span></li>`;
        });
    } catch (e) {
        console.error(e);
    }
}

async function registrarAvaliacao() {
    const aluno = document.getElementById("aluno").value;
    const nota = document.getElementById("nota").value;
    if (aluno.trim() === "" || nota.trim() === "") { alert("Preencha todos os campos."); return; }
    try {
        await fetch(API, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ nomeAluno: aluno, nota: parseFloat(nota) })
        });
        document.getElementById("aluno").value = "";
        document.getElementById("nota").value = "";
        listarAvaliacoes();
    } catch (e) {
        alert("Erro ao registrar avaliação.");
    }
}

listarAvaliacoes();