// Charts initialization
let nutritionChart;
let progressChart;

// Initialize charts on page load
document.addEventListener('DOMContentLoaded', function() {
    initializeCharts();
    loadGoals();
    loadLatestData();
    loadHistory();
});

function initializeCharts() {
    const ctx1 = document.getElementById('nutritionChart').getContext('2d');
    const ctx2 = document.getElementById('progressChart').getContext('2d');

    nutritionChart = new Chart(ctx1, {
        type: 'line',
        data: {
            labels: [],
            datasets: [{
                label: 'Calorías',
                data: [],
                borderColor: '#1fb6b1',
                tension: 0.4
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false
        }
    });

    progressChart = new Chart(ctx2, {
        type: 'doughnut',
        data: {
            labels: ['Proteínas', 'Carbohidratos'],
            datasets: [{
                data: [0, 0],
                backgroundColor: ['#1fb6b1', '#7b8a99']
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false
        }
    });
}

function toggleGoalsForm() {
    const form = document.getElementById('goalsForm');
    form.style.display = form.style.display === 'none' ? 'block' : 'none';
}

function updateGoals() {
    const goals = {
        caloriesGoal: document.getElementById('caloriesGoal').value,
        proteinsGoal: document.getElementById('proteinsGoal').value,
        carbohydratesGoal: document.getElementById('carbsGoal').value
    };

    fetch('/nutrition/goals', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(goals)
    })
    .then(response => response.json())
    .then(data => {
        alert('Metas actualizadas correctamente');
        updateProgressBars();
        toggleGoalsForm();
    })
    .catch(error => {
        console.error('Error:', error);
        alert('Error al actualizar las metas');
    });
}

function loadGoals() {
    fetch('/nutrition/goals')
        .then(response => response.json())
        .then(data => {
            document.getElementById('caloriesGoal').value = data.caloriesGoal;
            document.getElementById('proteinsGoal').value = data.proteinsGoal;
            document.getElementById('carbsGoal').value = data.carbohydratesGoal;
            updateProgressBars();
        })
        .catch(error => console.error('Error:', error));
}

function updateProgressBars() {
    const calories = document.getElementById('total-calories').textContent;
    const proteins = document.getElementById('total-proteins').textContent;
    const carbs = document.getElementById('total-carbs').textContent;
    
    const caloriesGoal = document.getElementById('caloriesGoal').value;
    const proteinsGoal = document.getElementById('proteinsGoal').value;
    const carbsGoal = document.getElementById('carbsGoal').value;

    document.getElementById('calories-progress').style.width = 
        `${Math.min((calories / caloriesGoal) * 100, 100)}%`;
    document.getElementById('proteins-progress').style.width = 
        `${Math.min((proteins / proteinsGoal) * 100, 100)}%`;
    document.getElementById('carbs-progress').style.width = 
        `${Math.min((carbs / carbsGoal) * 100, 100)}%`;
}

function loadLatestData() {
    fetch('/nutrition/latest')
        .then(response => response.json())
        .then(data => {
            updateUI(data);
        })
        .catch(error => console.error('Error:', error));
}

function updateNutrition() {
    const nutritionData = {
        calories: document.getElementById('calories').value,
        proteins: document.getElementById('proteins').value,
        carbohydrates: document.getElementById('carbs').value
    };

    // Validar datos
    if (!nutritionData.calories || !nutritionData.proteins || !nutritionData.carbohydrates) {
        alert('Por favor, ingresa todos los valores');
        return;
    }

    fetch('/nutrition/add', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            calories: parseFloat(nutritionData.calories),
            proteins: parseFloat(nutritionData.proteins),
            carbohydrates: parseFloat(nutritionData.carbohydrates)
        })
    })
    .then(response => response.json())
    .then(data => {
        updateUI(data);
        updateCharts();
        loadHistory();
        // Limpiar formulario
        document.getElementById('calories').value = '';
        document.getElementById('proteins').value = '';
        document.getElementById('carbs').value = '';
    })
    .catch(error => {
        console.error('Error:', error);
        alert('Error al actualizar los datos');
    });
}

function updateUI(data) {
    document.getElementById('total-calories').textContent = data.calories || 0;
    document.getElementById('total-proteins').textContent = data.proteins || 0;
    document.getElementById('total-carbs').textContent = data.carbohydrates || 0;
    updateProgressBars();
}

function loadHistory() {
    fetch('/nutrition/history')
        .then(response => response.json())
        .then(data => {
            const historyList = document.getElementById('nutritionHistory');
            historyList.innerHTML = '';
            
            data.slice(-5).reverse().forEach(item => {
                const date = new Date(item.dateTime).toLocaleDateString();
                const historyItem = document.createElement('div');
                historyItem.className = 'history-item';
                historyItem.innerHTML = `
                    <span>${date}</span>
                    <span>🔥 ${item.calories} kcal</span>
                    <span>🥩 ${item.proteins}g</span>
                    <span>🥖 ${item.carbohydrates}g</span>
                `;
                historyList.appendChild(historyItem);
            });

            updateCharts(data);
        })
        .catch(error => console.error('Error:', error));
}

function updateCharts(data) {
    if (!data || !data.length) return;

    // Actualizar gráfico de línea
    nutritionChart.data.labels = data.slice(-7).map(item => 
        new Date(item.dateTime).toLocaleDateString()
    );
    nutritionChart.data.datasets[0].data = data.slice(-7).map(item => 
        item.calories
    );
    nutritionChart.update();

    // Actualizar gráfico de dona
    const latest = data[data.length - 1];
    progressChart.data.datasets[0].data = [
        latest.proteins,
        latest.carbohydrates
    ];
    progressChart.update();
}
