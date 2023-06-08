import React from 'react';
import {Inject,ScheduleComponent,Day,Week,WorkWeek,Month,Agenda,EventSettingsModel} from "@syncfusion/ej2-react-schedule";

const MyCalendar = () => {
    const  localData: EventSettingsModel = {
        dataSource: [{
            EndTime: new Date(2023, 0, 11, 6, 30),
            StartTime: new Date(2023, 0, 11, 4, 0)
        }]
    };
    return (
        <ScheduleComponent currentView={'Month'} eventSettings={localData} selectedDate={new Date()}>
            <Inject services={[Day,Week,WorkWeek,Month,Agenda]}/>
        </ScheduleComponent>
    );
};

export default MyCalendar;