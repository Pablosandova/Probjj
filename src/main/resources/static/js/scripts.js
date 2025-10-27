// Simple sparkline using canvas
(function(){
  const canvas = document.getElementById('sparkline');
  if(!canvas) return;
  const ctx = canvas.getContext('2d');
  const w = canvas.width; const h = canvas.height;
  const data = [30,50,40,70,60,80,55,70,65,75,60,70,68];
  ctx.strokeStyle='#47a6d6'; ctx.lineWidth=2; ctx.beginPath();
  data.forEach((v,i)=>{
    const x = (i/(data.length-1))*w;
    const y = h - (v/100)*h - 8;
    if(i===0) ctx.moveTo(x,y); else ctx.lineTo(x,y);
  });
  ctx.stroke();
})();

// Donut animation for steps
(function(){
  const donut = document.querySelector('.donut');
  if(!donut) return;
  const steps = Number(donut.dataset.steps)||8000;
  const goal = Number(donut.dataset.goal)||10000;
  const percent = Math.round((steps/goal)*100);
  const angle = (percent/100)*360;
  // set custom property for conic gradient
  donut.style.setProperty('--p', percent/100);

  // Animate number
  const el = document.getElementById('steps-number');
  let cur = 0; const dur = 1200; const start = performance.now();
  function tick(t){
    const elapsed = t-start; const prog = Math.min(1, elapsed/dur);
    cur = Math.round(prog*steps);
    el.textContent = cur.toLocaleString();
    if(prog<1) requestAnimationFrame(tick);
  }
  requestAnimationFrame(tick);
})();

// Small accessibility tweak: keyboardable nav
(function(){
  const nav = document.querySelectorAll('.main-nav a');
  nav.forEach(a=>{ a.setAttribute('tabindex','0'); a.addEventListener('keypress', e=>{ if(e.key==='Enter') a.click(); }) });
})();
