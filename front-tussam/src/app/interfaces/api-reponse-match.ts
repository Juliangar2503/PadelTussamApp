import { Match } from "./match";

export interface ApiReponseMatch {
    data: Match;
    message: string | null;
    statusCode: {
        value: number;
        description: string;
    };
}
