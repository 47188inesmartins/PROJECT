import React, {useState} from 'react';
import { Calendar, momentLocalizer } from 'react-big-calendar';
import moment from 'moment';
import 'react-big-calendar/lib/css/react-big-calendar.css';
import '../Style/MyCalendar.css'
import {Modal} from "reactstrap";

const localizer = momentLocalizer(moment);

interface Event {
    title: string;
    start: Date;
    end: Date;
}

const MyCalendar: React.FC = () => {
    const [selectedEvent, setSelectedEvent] = useState<Event | null>(null);
    const [modalIsOpen, setModalIsOpen] = useState(false);


    const closeModal = () => {
        setModalIsOpen(false);
    };

    const handleEventClick = (event: Event) => {
        setSelectedEvent(event);
        setModalIsOpen(true);
    };

    const myEventsList: Event[] = [
        {
            title: 'Evento 1',
            start: new Date(2023, 5, 6, 10, 0),
            end: new Date(2023, 5, 6, 12, 0),
        },
        {
            title: 'Evento 2',
            start: new Date(2023, 5, 7, 14, 0),
            end: new Date(2023, 5, 7, 16, 0),
        },
    ];

    const calendarStyle = {
        height: 500,
        backgroundColor: '#84A1C7',
        // Adicione outras propriedades de estilo conforme necessário
    };

    const EventComponent: React.FC<{ event: Event }> = ({ event }) => {
        const isSelected = selectedEvent === event;

        return (
            <div
                style={{ background: isSelected ? 'lightblue' : 'transparent' }}
                onClick={() => handleEventClick(event)}
            >
                <div>{event.title}</div>
                {isSelected && (
                    <>
                        <div>Hora de início: {event.start.toLocaleTimeString()}</div>
                        <div>Hora de fim: {event.end.toLocaleTimeString()}</div>
                    </>
                )}
            </div>
        );
    };

    const selectedEventInfo = selectedEvent ? (
        <div>
            <h3>Detalhes do Evento:</h3>
            <div>Título: {selectedEvent.title}</div>
            <div>Hora de início: {selectedEvent.start.toLocaleTimeString()}</div>
            <div>Hora de fim: {selectedEvent.end.toLocaleTimeString()}</div>
        </div>
    ) : null;

    return (
        <div>
            <Calendar
                localizer={localizer}
                events={myEventsList}
                startAccessor="start"
                endAccessor="end"
                style={calendarStyle}
                components={{
                    event: EventComponent,
                }}
            />
            <Modal
                isOpen={modalIsOpen}
                onRequestClose={closeModal}
                contentLabel="Detalhes do Evento"
            >
                {selectedEventInfo}
                <button onClick={closeModal}>Fechar</button>
            </Modal>
        </div>

    );
};

export default MyCalendar;
