import React from 'react';
import {Inject,ScheduleComponent,Day,Week,WorkWeek,Month,Agenda,EventSettingsModel} from "@syncfusion/ej2-react-schedule";

const CalendarComponent = () => {
    const  localData: EventSettingsModel = {
        dataSource: [{
            EndTime: new Date(2023, 0, 11, 6, 30),
            StartTime: new Date(2023, 0, 11, 4, 0)
        }]
    };
    const calendarStyle = {
        fontFamily: 'Arial, sans-serif',
        fontSize: '14px',
        color: '#333',
        backgroundColor: '#fff',
        border: '1px solid #ccc',
        borderRadius: '4px',
        padding: '10px'
    };

    return (
        <div style={calendarStyle}>
        <ScheduleComponent currentView={'Month'} eventSettings={localData} selectedDate={new Date()}>
            <Inject services={[Day,Week,WorkWeek,Month,Agenda]}/>
        </ScheduleComponent>
        </div>
    );
};

export default CalendarComponent;