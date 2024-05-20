import { Player } from "./player";

export interface ApiResponsePlayers {
    data: Player[];
    message: string | null;
    statusCode: {
        value: number;
        description: string;
    };
}
