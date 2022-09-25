import { Viagem } from '../Viagem/viagem'


export interface Passagem{
    id: string;
    viagemId: Viagem;
    horaPassagem: number;
    abreviaturaNo: string
}