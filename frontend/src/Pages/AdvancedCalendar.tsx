import React, {useEffect, useState} from 'react';
import { Calendar, momentLocalizer } from 'react-big-calendar';
import moment from 'moment';
import 'react-big-calendar/lib/css/react-big-calendar.css';
import '../Style/MyCalendar.css'
import {Modal} from "reactstrap";
import {Fetch} from "../Utils/useFetch";
import {useParams} from "react-router-dom";

const localizer = momentLocalizer(moment);

interface Event {
    title: string;
    start: Date;
    end: Date;
}

const MyCalendar: React.FC = () => {
    const [selectedEvent, setSelectedEvent] = useState<Event | null>(null);
    const [modalIsOpen, setModalIsOpen] = useState(false);

    const id = useParams().id

    const mapDataToEvents = (data: any[]): Event[] => {
        if (!data) return
        return data.map((item) => {
            const appDateParts = item.appDate.split('-').map(Number);
            const appBeginHourParts = item.appBeginHour.split(':').map(Number);
            const appEndHourParts = item.appEndHour.split(':').map(Number);

            const start = new Date(
                appDateParts[0],
                appDateParts[1] - 1,
                appDateParts[2],
                appBeginHourParts[0],
                appBeginHourParts[1],
            );

            const end = new Date(
                appDateParts[0],
                appDateParts[1] - 1,
                appDateParts[2],
                appEndHourParts[0],
                appEndHourParts[1],
            );

            //elemento do tipo event so pode ter title,start,end
            return {
                title: item.employee,
                start,
                end,
            };
        });
    };

    const mapDataToEventsHolidays = (data: any[]): Event[] => {
        if (!data) return []
        return data.map((item) => {
            const start = moment(item.dateBegin).toDate();
            const end = moment(item.dateEnd).toDate();

            const durationInDays = moment(end).diff(start, 'days') + 1; // +1 para incluir o último dia

            // Gerar um array de datas entre o início e o fim das férias
            const dates = Array.from({ length: durationInDays }, (_, index) =>
                moment(start).add(index, 'days').toDate()
            );

            return {
                title: "VACATION",
                start,
                end,
                color: 'red',
                dates
            };
        });
    };

    const closeModal = () => {
        setModalIsOpen(false);
    };

    const handleEventClick = (event: Event) => {
        setSelectedEvent(event);
        setModalIsOpen(true);
    };

    const EventComponent: React.FC<{ event: Event }> = ({event}) => {
        const isSelected = selectedEvent === event;
        return (
            <div
                style={{background: isSelected ? '#87c293' : 'transparent'}}
                onClick={() => handleEventClick(event)}
            >
                <div>{event.title}</div>
                {isSelected && (
                    <>
                        <div>Begin Hour: {event.start.toLocaleTimeString()}</div>
                        <div>End Hour: {event.end.toLocaleTimeString()}</div>
                    </>
                )}
            </div>
        );
    };


    const selectedEventInfo = selectedEvent ? (
        <div>
            <h3>Events details:</h3>
            <div>Title: {selectedEvent.title}</div>
            <div>Begin hour: {selectedEvent.end.toLocaleTimeString()}</div>
            <div>End hour: {selectedEvent.start.toLocaleTimeString()}</div>
        </div>
    ) : null;

    const response = Fetch(`company/${id}/appointments-list`, 'GET')
    const responseVacation = Fetch(`company/${id}/vacations`, 'GET')

    const calendarStyle1 = {
        height: 500,
        backgroundColor: '#8faacf',
        margin: '20px',
        color: 'black', // Definir a cor preta para todos os elementos do calendário
        '.rbc-calendar .rbc-month-view .rbc-date-cell:hover': {
            backgroundColor: 'red', // Cor de fundo ao passar o mouse
            color: 'white', // Cor do texto ao passar o mouse
        },
    };

    const getEventStyle = (event, start, end, isSelected) => {
        let backgroundColor = '';
        let color = '';

        if (event.title === 'VACATION') {
            backgroundColor = '#FF6961';
            color = 'white';
        } else {
            backgroundColor = '#CBC4A4';
            color = 'black';
        }

        return {
            style: {
                backgroundColor,
                color,
            },
        };
    };

    return (
        <div style={{margin: '20px'}}>
            {response.response &&  responseVacation.response? (
                <>
                    <Calendar
                        localizer={localizer}
                        events={[...mapDataToEvents(response.response),...mapDataToEventsHolidays(responseVacation.response)]}
                        startAccessor="start"
                        endAccessor="end"
                        style={calendarStyle1}
                        components={{
                            event: EventComponent,
                        }}
                        eventPropGetter={getEventStyle}
                    />
                    <Modal
                        isOpen={modalIsOpen}
                        onRequestClose={closeModal}
                        contentLabel="Appointment Details"
                    >
                        {selectedEventInfo}
                        <button onClick={closeModal}>
                            Close
                        </button>
                    </Modal>
                </>
            ) : (
                <Calendar
                    localizer={localizer}
                    startAccessor="start"
                    endAccessor="end"
                    style={calendarStyle1}
                />
            )}
        </div>
    );
}
export default MyCalendar;
