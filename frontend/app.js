// Purpose: Connect the page to the Spring Boot API and manage event browsing, filtering, and creation.
// Language: JavaScript.
// Author: Lewis McDonald.
// Date: 2026-10-08.

const API_URL = 'http://localhost:8080/api/events';

const eventsList = document.getElementById('eventsList');
const searchInput = document.getElementById('searchInput');
const categoryFilter = document.getElementById('categoryFilter');
const form = document.getElementById('eventForm');
const formMessage = document.getElementById('formMessage');
const refreshButton = document.getElementById('refreshButton');

let allEvents = [];

function formatDateTime(value) {
	if (!value) {
		return 'TBC';
	}

	const date = new Date(value);
	return new Intl.DateTimeFormat('en-GB', {
		dateStyle: 'medium',
		timeStyle: 'short'
	}).format(date);
}

function formatPrice(value) {
	if (value === null || value === undefined || value === '') {
		return 'Free';
	}

	const numberValue = Number(value);
	if (numberValue === 0) {
		return 'Free';
	}

	return `£${numberValue.toFixed(2)}`;
}

function updateStats(items) {
	document.getElementById('totalEvents').textContent = String(items.length);
	document.getElementById('upcomingEvents').textContent = String(
		items.filter((event) => new Date(event.startDateTime) > new Date()).length
	);
	document.getElementById('freeEvents').textContent = String(
		items.filter((event) => Number(event.price) === 0).length
	);
}

function getFilteredEvents() {
	const searchTerm = searchInput.value.trim().toLowerCase();
	const selectedCategory = categoryFilter.value;

	return allEvents.filter((event) => {
		const matchesSearch = !searchTerm ||
			event.title.toLowerCase().includes(searchTerm) ||
			event.venueName.toLowerCase().includes(searchTerm);

		const matchesCategory = selectedCategory === 'all' || event.category === selectedCategory;

		return matchesSearch && matchesCategory;
	});
}

function renderEvents() {
	const filteredEvents = getFilteredEvents();
	updateStats(filteredEvents);

	if (filteredEvents.length === 0) {
		eventsList.innerHTML = '<div class="empty-state">No events match the current search or filter.</div>';
		return;
	}

	eventsList.innerHTML = filteredEvents
		.map((event) => `
			<article class="event-card">
				<div class="event-meta">
					<span class="tag">${event.category}</span>
					<span>${event.venueName}</span>
				</div>
				<h4>${event.title}</h4>
				<div class="event-meta">
					<span>${formatDateTime(event.startDateTime)}</span>
					<span>to ${formatDateTime(event.endDateTime)}</span>
				</div>
				<p>${event.description}</p>
				<div class="event-meta">
					<span class="price-pill">${formatPrice(event.price)}</span>
					<a href="${event.websiteUrl}" target="_blank" rel="noreferrer">View event</a>
				</div>
			</article>
		`)
		.join('');
}

async function loadEvents() {
	try {
		const response = await fetch(API_URL);
		if (!response.ok) {
			throw new Error('The API did not respond successfully.');
		}

		allEvents = await response.json();
		renderEvents();
	} catch (error) {
		eventsList.innerHTML = `
			<div class="empty-state">
				Could not load events from the backend. Start the Spring Boot server on port 8080 first.
			</div>
		`;
		console.error(error);
	}
}

async function createEvent(eventData) {
	const response = await fetch(API_URL, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(eventData)
	});

	if (!response.ok) {
		const errorText = await response.text();
		throw new Error(errorText || 'The event could not be created.');
	}

	return response.json();
}

form.addEventListener('submit', async (event) => {
	event.preventDefault();

	const formData = new FormData(form);
	const payload = {
		title: formData.get('title'),
		description: formData.get('description'),
		category: formData.get('category'),
		venueName: formData.get('venueName'),
		startDateTime: formData.get('startDateTime'),
		endDateTime: formData.get('endDateTime'),
		price: Number(formData.get('price')),
		websiteUrl: formData.get('websiteUrl')
	};

	try {
		formMessage.textContent = 'Saving your event...';
		formMessage.className = 'form-message';

		await createEvent(payload);
		form.reset();
		formMessage.textContent = 'Event created successfully.';
		formMessage.className = 'form-message success';

		await loadEvents();
	} catch (error) {
		formMessage.textContent = error.message;
		formMessage.className = 'form-message error';
	}
});

searchInput.addEventListener('input', renderEvents);
categoryFilter.addEventListener('change', renderEvents);
refreshButton.addEventListener('click', loadEvents);

loadEvents();