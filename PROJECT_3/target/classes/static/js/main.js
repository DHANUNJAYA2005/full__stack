document.addEventListener('DOMContentLoaded', function() {
    
    // ==========================================
    // Calendar Toggle Logic
    // ==========================================
    const toggleBtn = document.getElementById('toggleViewBtn');
    const gridView = document.getElementById('eventsGrid');
    const calendarView = document.getElementById('calendarView');
    const filterBar = document.getElementById('filterBar');
    let calendarInit = false;
    let calendar;

    if (toggleBtn && gridView && calendarView) {
        toggleBtn.addEventListener('click', function() {
            if (gridView.style.display !== 'none') {
                // Switch to Calendar View
                gridView.style.display = 'none';
                if(filterBar) filterBar.style.display = 'none';
                calendarView.style.display = 'block';
                toggleBtn.innerHTML = '<i class="fas fa-th-large"></i> Grid View';
                
                if (!calendarInit && window.FullCalendar) {
                    initCalendar();
                    calendarInit = true;
                }
            } else {
                // Switch to Grid View
                gridView.style.display = 'flex'; // row usually uses flex
                if(filterBar) filterBar.style.display = 'block';
                calendarView.style.display = 'none';
                toggleBtn.innerHTML = '<i class="fas fa-calendar-alt"></i> Calendar View';
            }
        });
    }

    function initCalendar() {
        const calendarEl = document.getElementById('calendar');
        calendar = new FullCalendar.Calendar(calendarEl, {
            initialView: 'dayGridMonth',
            headerToolbar: {
                left: 'prev,next today',
                center: 'title',
                right: 'dayGridMonth,timeGridWeek,timeGridDay'
            },
            events: function(fetchInfo, successCallback, failureCallback) {
                fetch('/api/events')
                    .then(response => response.json())
                    .then(data => {
                        const events = data.map(event => {
                            let startDateTime = event.eventDate;
                            if (event.time) {
                                // Add leading zero if needed and ensure standard format
                                let t = event.time.length === 5 ? event.time + ":00" : event.time;
                                startDateTime += 'T' + t;
                            }
                            return {
                                id: event.id,
                                title: event.title,
                                start: startDateTime,
                                url: '/events/detail/' + event.id
                            };
                        });
                        successCallback(events);
                    })
                    .catch(err => {
                        console.error('Error fetching events:', err);
                        failureCallback(err);
                    });
            }
        });
        calendar.render();
    }

    // ==========================================
    // Chatbot Logic
    // ==========================================
    const chatbotHeader = document.getElementById('chatbot-header');
    const chatbotContainer = document.getElementById('chatbot-container');
    const chatMessages = document.getElementById('chatbot-messages');
    const chatInput = document.getElementById('chatInput');
    const chatSendBtn = document.getElementById('chatSendBtn');

    if (chatbotHeader && chatbotContainer) {
        // Collapse/Expand Chatbot
        chatbotHeader.addEventListener('click', function() {
            chatbotContainer.classList.toggle('collapsed');
        });

        // Initialize greeting
        addMessage('bot', "Hi there! I'm your campus event assistant. How can I help you today?");

        function addMessage(sender, text) {
            const msgDiv = document.createElement('div');
            msgDiv.classList.add('chat-message');
            msgDiv.classList.add(sender === 'user' ? 'chat-user' : 'chat-bot');
            msgDiv.textContent = text;
            chatMessages.appendChild(msgDiv);
            chatMessages.scrollTop = chatMessages.scrollHeight;
        }

        function sendMessage() {
            const text = chatInput.value.trim();
            if (!text) return;
            
            addMessage('user', text);
            chatInput.value = '';

            // Loading indicator
            const loadingDiv = document.createElement('div');
            loadingDiv.classList.add('chat-message', 'chat-bot');
            loadingDiv.innerHTML = '<i class="fas fa-ellipsis-h fa-fade"></i>';
            chatMessages.appendChild(loadingDiv);
            chatMessages.scrollTop = chatMessages.scrollHeight;

            fetch('/api/chatbot/message', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ message: text })
            })
            .then(res => res.json())
            .then(data => {
                chatMessages.removeChild(loadingDiv);
                addMessage('bot', data.reply);
            })
            .catch(err => {
                chatMessages.removeChild(loadingDiv);
                addMessage('bot', "Sorry, I'm having trouble connecting right now.");
            });
        }

        chatSendBtn.addEventListener('click', sendMessage);
        chatInput.addEventListener('keypress', function(e) {
            if (e.key === 'Enter') sendMessage();
        });
    }

});
