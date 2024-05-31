export interface Player {
    id: number;
    name: string;
    lastName: string;
    email: string;
    location?: string;
    nickname: string;
    avatar?: string;
    points?: number;
    active: boolean;
    gameWon: Number,
    gameLost : Number,
    gameDifference: Number,
    gamePlayed: Number,
    createdAt: string;
    leagueId?: number;
    roleId?: number;
    authToken?: string | null;
}
