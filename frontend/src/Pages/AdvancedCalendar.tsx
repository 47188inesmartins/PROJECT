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
            <div>Begin hour: {selectedEvent.start.toLocaleTimeString()}</div>
            <div>End hour: {selectedEvent.end.toLocaleTimeString()}</div>
        </div>
    ) : null;

    const response = Fetch(`company/${id}/appointments-list`, 'GET')
    const responseVacation = Fetch(`company/${id}/vacations`, 'GET')

    const calendarStyle1 = {
        height: 500,
        backgroundColor: '#BEDAE3',
        margin: '20px',
    };

    const pageStyle = {
        backgroundColor: '#BEDAE3', // Defina a cor de fundo da página aqui
        margin: '20px',
    };

    const buttonStyle = {
        borderRadius: '10px',
        backgroundColor: '#FFFFFF',
        color: 'white',
        padding: '10px',
    };
    const dayPropGetter = (date: Date) => {
        const currentDate = moment(date);
        const currentMonth = moment().startOf('month');
        const isDifferentMonth = currentDate.isBefore(currentMonth) || currentDate.isAfter(currentMonth.endOf('month'));

        if (isDifferentMonth) {
            return {
                className: 'other-month-day',
                style: {
                    backgroundColor: '#dcdcdc',
                },
            };
        }

        return {};
    };

    const toolbar = (toolbar: any) => {
        const buttonStyle = {
            borderRadius: '10px',
            backgroundColor: '#C4E9DA', // Define a cor dos botões aqui
            color: 'white',
            padding: '10px',
        };
    }
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
                        dayPropGetter={dayPropGetter} // Aplica a personalização dos dias de outro mês
                        toolbar={toolbar} // Aplica a personalização da toolbar
                    />
                    <Modal
                        isOpen={modalIsOpen}
                        onRequestClose={closeModal}
                        contentLabel="Appointment Details"
                    >
                        {selectedEventInfo}
                        <button style={buttonStyle} onClick={closeModal}>
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
                    dayPropGetter={dayPropGetter} // Aplica a personalização dos dias de outro mês
                    toolbar={toolbar} // Aplica a personalização da toolbar
                />
            )}
        </div>
    );
}
export default MyCalendar;
