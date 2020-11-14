import { Component, OnDestroy, OnInit } from '@angular/core';
import { NdrConverterService } from "../services/ndr-converter.service";
import { RxStompService } from "@stomp/ng2-stompjs";
import { Message } from '@stomp/stompjs';
import { Subscription } from "rxjs";
import { DomSanitizer } from "@angular/platform-browser";
import { saveAs } from 'file-saver';

export interface Facility {
    id: number;
    name: string;
    selected: boolean;
}

@Component({
    selector: 'ndr-converter',
    templateUrl: './ndr-convert.component.html'
})
export class NdrConverterComponent implements OnInit, OnDestroy {
    private topicSubscription: Subscription;
    facilities: Facility[] = [];
    files: string[];
    running = false;
    message: any;
    finished = false;

    constructor(private ndrService: NdrConverterService, private stompService: RxStompService, private domSanitizer: DomSanitizer) {
    }

    ngOnInit() {
        this.ndrService.listFacilities().subscribe(res => this.facilities = res);
        this.topicSubscription = this.stompService.watch("/topic/ndr-status").subscribe((msg: Message) => {
            if (msg.body === 'start') {
                this.running = true
            } else if (msg.body === 'end') {
                this.running = false;
                this.message = "Conversion finished; download files from Download tab";
                this.finished = true;
                this.ndrService.listFiles().subscribe(res => {
                    this.files = res;
                })
            } else {
                this.message = msg.body;
                this.running = true;
            }
        })
    }

    selected(): boolean {
        return this.facilities.filter(f => f.selected).length > 0
    }

    download(name: string) {
        this.ndrService.download(name).subscribe(res => {
            const file = new File([res], name + '.zip', {type: 'application/octet-stream'});
            saveAs(file);
        });
    }

    tabChanged(event) {
        if (event.index === 1) {
            this.ndrService.listFiles().subscribe(res => {
                this.files = res;
            })
        }
    }

    convert() {
        let ids = this.facilities.filter(f => f.selected)
            .map(f => f.id);
        this.ndrService.convert(ids).subscribe()
    }

    deduplicate() {
        this.running = true;
        this.ndrService.deduplicate().subscribe(res => this.running = false)
    }

    ngOnDestroy(): void {
        this.topicSubscription.unsubscribe()
    }
}
