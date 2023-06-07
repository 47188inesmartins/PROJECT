import * as React from 'react';
import { Calendar, DateLocalizer, momentLocalizer } from 'react-big-calendar';
import 'react-big-calendar/lib/css/react-big-calendar.css';
import moment from 'moment';

const localizer: DateLocalizer = momentLocalizer(moment);

interface Event {
    title: string;
    start: Date;
    end: Date;
}

const MyCalendar: React.FC = () => {
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
        </div>
    );
};

const EventComponent: React.FC<{ event: Event }> = ({ event }) => {
    return (
        <div>
            <div>{event.title}</div>
            <div>Hora de início: {event.start.toLocaleTimeString()}</div>
            <div>Hora de fim: {event.end.toLocaleTimeString()}</div>
        </div>
    );
};

export default MyCalendar;
