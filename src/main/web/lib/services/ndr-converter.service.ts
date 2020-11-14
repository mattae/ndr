import { Inject, Injectable } from "@angular/core";
import { HttpClient, HttpParams } from "@angular/common/http";
import { SERVER_API_URL_CONFIG, ServerApiUrlConfig } from "@lamis/web-core";
import { Facility } from "../components/ndr-converter.component";
import { Observable } from "rxjs";

@Injectable()
export class NdrConverterService {
    public resourceUrl = '';

    constructor(private http: HttpClient, @Inject(SERVER_API_URL_CONFIG) private serverUrl: ServerApiUrlConfig) {
        this.resourceUrl = serverUrl.SERVER_API_URL + '/api/ndr';
    }

    convert(ids: number[]) {
        let params = new HttpParams();
        ids.forEach(id => params = params.append("ids", id.toString()));
        return this.http.get(`${this.resourceUrl}/run`, {params})
    }

    listFacilities() {
        return this.http.get<Facility[]>(`${this.resourceUrl}/list-facilities`)
    }

    download(name: string): Observable<Blob> {
        return this.http.get(`${this.resourceUrl}/download/${name}`, {responseType: 'blob'})
    }

    listFiles() {
        return this.http.get<string[]>(`${this.resourceUrl}/list-files`)
    }

    deduplicate() {
        return this.http.get(`${this.resourceUrl}/remove-duplicates`)
    }
}